package com.example.viredapp.utilities

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.database.AppLocalCache
import com.example.viredapp.database.FeedLocalCache
import com.example.viredapp.database.RequestLocalCache
import com.example.viredapp.model.FeedViewModelFactory
import com.example.viredapp.model.FriendsViewModelFactory
import com.example.viredapp.model.RequestViewModelFactory
import com.example.viredapp.services.FeedRepository
import com.example.viredapp.services.FriendsRepository
import com.example.viredapp.services.RequestRepository
import java.util.concurrent.Executors

object InjectorUtils{

    private fun provideFeedCache(context: Context):FeedLocalCache{
        val database = AppDatabase.getInstance(context)
        return FeedLocalCache(database.feedDao(),Executors.newSingleThreadExecutor())
    }

    private fun provideFeedRepository(context: Context):FeedRepository{
        return FeedRepository(provideFeedCache(context))
    }

    fun provideFeedViewModel(context: Context):ViewModelProvider.Factory{
        return FeedViewModelFactory(provideFeedRepository(context))
    }




    /**
    * Create an instance of [AppLocalCache]
    * */
    private fun provideCache(context: Context):AppLocalCache{
        val database = AppDatabase.getInstance(context)
        return AppLocalCache(database.friendsDao(),Executors.newSingleThreadExecutor())
    }

    private fun provideFriendsRepository(context: Context):FriendsRepository{
        return FriendsRepository(provideCache(context))
    }

    fun provideFriendsViewModel(context: Context):ViewModelProvider.Factory{
        return FriendsViewModelFactory(provideFriendsRepository(context))
    }

    /**
    * Create an instance of [RequestLocalCache]
    * */
    private fun provideRequestCache(context: Context):RequestLocalCache{
        val database = AppDatabase.getInstance(context)
        return RequestLocalCache(database.requestDao(), Executors.newSingleThreadExecutor())
    }

     fun provideRequestRepository(context: Context):RequestRepository{
        return RequestRepository(provideRequestCache(context))
    }

    fun provideRequestViewModel(context: Context):ViewModelProvider.Factory{
        return RequestViewModelFactory(provideRequestRepository(context))
    }
}
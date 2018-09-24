package com.example.viredapp.utilities

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.database.AppLocalCache
import com.example.viredapp.model.FeedViewModelFactory
import com.example.viredapp.model.FriendsViewModelFactory
import com.example.viredapp.model.UserViewModelFactory
import com.example.viredapp.services.FeedRepository
import com.example.viredapp.services.FriendsRepository
import com.example.viredapp.services.UserRepository
import java.util.concurrent.Executors

object InjectorUtils{


     fun getFeedRepository(context: Context):FeedRepository{
        return FeedRepository.getInstance(AppDatabase.getInstance(context).feedDao())
    }

    fun provideViewModelFactory(context: Context):ViewModelProvider.Factory{
        return FeedViewModelFactory(getFeedRepository(context))
    }

    private fun getUserRepository(context: Context):UserRepository{
        return UserRepository()
    }

    fun provideUserViewModel(context: Context):ViewModelProvider.Factory{
        return UserViewModelFactory(getUserRepository(context))
    }

    /*
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



}
package com.example.viredapp.utilities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.database.AppLocalCache
import com.example.viredapp.db.FeedDao
import com.example.viredapp.model.Feed
import com.example.viredapp.model.FeedViewModel
import com.example.viredapp.model.FeedViewModelFactory
import com.example.viredapp.services.FeedRepository
import java.util.concurrent.Executors

object InjectorUtils{


    private fun getFeedRepository(context: Context):FeedRepository{
        return FeedRepository.getInstance(AppDatabase.getInstance(context).feedDao())
    }

    fun provideViewModelFactory(context: Context):ViewModelProvider.Factory{
        return FeedViewModelFactory(getFeedRepository(context))
    }
}
package com.example.viredapp.utilities

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.model.FeedViewModelFactory
import com.example.viredapp.model.UserViewModelFactory
import com.example.viredapp.services.FeedRepository
import com.example.viredapp.services.UserRepository

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


}
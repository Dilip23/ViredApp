package com.example.viredapp.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.viredapp.services.FeedRepository


/**
 * Factory for creating a [FeedViewModel] with a constructor that takes a [FeedRepository].
 */
class FeedViewModelFactory(
        private val repository: FeedRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) :T{
        if(modelClass.isAssignableFrom(FeedViewModel::class.java)){
            return FeedViewModel(repository) as T
        }
        else{
            throw IllegalArgumentException("Uknown ViewModel Class")
        }
    }
}
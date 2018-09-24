package com.example.viredapp.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.viredapp.services.FriendsRepository

class FriendsViewModelFactory(private val friendsRepository: FriendsRepository):ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FriendsViewModel::class.java)){
            return FriendsViewModel(friendsRepository) as T
        }else{
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}
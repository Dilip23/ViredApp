package com.example.viredapp.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.viredapp.services.UserRepository

class UserViewModelFactory(
        private val repository: UserRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)){
            return UsersViewModel(repository) as T
    }
    else
    {
        throw IllegalArgumentException("Uknown ViewModel Class")
    }
}
}
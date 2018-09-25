package com.example.viredapp.model

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.viredapp.services.RequestRepository

class RequestViewModelFactory(private val requestRepository: RequestRepository):ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RequestViewModel::class.java)){
            return RequestViewModel(requestRepository) as T
        }
        else{
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}
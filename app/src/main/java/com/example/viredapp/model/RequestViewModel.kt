package com.example.viredapp.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList
import com.example.viredapp.db.Request
import com.example.viredapp.services.RequestRepository

/**
 *View Model to communicate with UI{RequestFragment} and Repository{RequestRepository}
 * */


class RequestViewModel(private val repository: RequestRepository) : ViewModel() {

    fun observeRequests():LiveData<PagedList<Request>> = repository.showFriends()

    /**
     * Send Delete Request to Server
     * */
    fun deleteRequest(id:Int){
        repository.sendDeleteRequest(id)
    }




}

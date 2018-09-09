package com.example.viredapp.services

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.db.Request
import com.example.viredapp.db.RequestDao

class RequestRepository private constructor(private val requestDao: RequestDao){

    //Get Requests
    //fun getPendingRequests():LiveData<PagedList<Request>> = requestDao.showPendingRequests()


    companion object {
        //For Singleton Instantiation
        @Volatile private var INSTANCE:RequestRepository? = null

        fun getInstance(requestDao: RequestDao){
            INSTANCE?: synchronized(this){
                INSTANCE?: RequestRepository(requestDao).also { INSTANCE = it }
            }
        }
    }

}
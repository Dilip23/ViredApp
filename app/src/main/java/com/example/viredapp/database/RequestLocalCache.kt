package com.example.viredapp.database

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import com.example.viredapp.db.Request
import com.example.viredapp.db.RequestDao
import timber.log.Timber
import java.util.concurrent.Executor

class RequestLocalCache(
        private val requestDao: RequestDao,
        private val ioExecutor: Executor
){

    fun insert(request: List<Request>){
        ioExecutor.execute {
            Timber.d("Inserting ${request} into request table")
            requestDao.insert(request)
        }
    }

    fun showRequests():DataSource.Factory<Int,Request>{
        return requestDao.showPendingRequests()
    }

    fun acceptRequest(request: Request){
        ioExecutor.execute {
            requestDao.acceptRequest(request)
        }
    }

    fun updateSpamRequest(request: Request){
        ioExecutor.execute {
            requestDao.spamRequest(request)
        }
    }

}
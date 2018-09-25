package com.example.viredapp.services

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.viredapp.adapters.RequestBoundaryCallBack
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.database.RequestLocalCache
import com.example.viredapp.db.Request
import com.example.viredapp.db.RequestDao

class RequestRepository(private val cache: RequestLocalCache){


    companion object {
        private const val DATABASE_QUERY_SIZE = 50
    }

    fun showFriends():LiveData<PagedList<Request>>{
        val dataSourceFactory = cache.showRequests()
        val boundaryCallback = RequestBoundaryCallBack(cache)
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_QUERY_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return data
    }


}
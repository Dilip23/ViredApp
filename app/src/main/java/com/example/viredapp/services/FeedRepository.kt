package com.example.viredapp.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.viredapp.db.FeedDao
import com.example.viredapp.db.feed
import com.example.viredapp.model.Feed
import com.example.viredapp.model.FeedResult
import com.example.viredapp.adapters.FeedBoundaryCallBack
import com.example.viredapp.database.FeedLocalCache
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executors

class FeedRepository (
        private val cache: FeedLocalCache
){
    companion object {
        private const val DEAULT_DATABASE_PAGE_SIZE = 20

    }


    fun showFeed():LiveData<PagedList<feed>>{
        Timber.d("showFeed() method called")
        val dataSourceFactory = cache.getAllFeed()
        val boundaryCallBack = FeedBoundaryCallBack(cache)
        val data = LivePagedListBuilder(dataSourceFactory, DEAULT_DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallBack)
                .build()
        return data
    }

}

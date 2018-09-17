package com.example.viredapp.adapters

import android.arch.paging.PagedList
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.db.feed
import com.example.viredapp.services.FeedRepository
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.MyApplication
import com.example.viredapp.utilities.UserClient
import timber.log.Timber

class FeedBoundaryCallBack(private val id:Int):PagedList.BoundaryCallback<feed>(){
    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
        private val feedRepository = FeedRepository.getInstance(AppDatabase.getInstance(MyApplication.getContext()).feedDao())
    }

    //We only append to data which is already in DB
    override fun onItemAtFrontLoaded(itemAtFront: feed) {
        Timber.i("onItemAtFrontLoaded()")
    }

    override fun onZeroItemsLoaded() {
        Timber.i("onZeroItemsLoaded()")
        apiClient.getFeed(NETWORK_PAGE_SIZE,0)
    }

    override fun onItemAtEndLoaded(itemAtEnd:feed) {
        Timber.i("onItemAtEndLoaded()")
        var offset:Int = itemAtEnd.id.toInt()
        apiClient.getFeed(NETWORK_PAGE_SIZE,offset)
    }


}
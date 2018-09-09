package com.example.viredapp.ui

import android.arch.paging.PagedList
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.db.feed
import com.example.viredapp.model.Feed
import com.example.viredapp.services.FeedRepository
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.MyApplication
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FeedBoundaryCallBack(private val id:Int):PagedList.BoundaryCallback<feed>(){
    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
        private val feedRepository = FeedRepository.getInstance(AppDatabase.getInstance(MyApplication.getContext()).feedDao())
    }


    override fun onZeroItemsLoaded() {
        feedRepository.getFeedResponse()
    }

    override fun onItemAtEndLoaded(itemAtEnd: feed) {
        feedRepository.getFeedResponse()
    }


}
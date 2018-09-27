package com.example.viredapp.adapters

import android.arch.paging.PagedList
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.database.FeedLocalCache
import com.example.viredapp.db.feed
import com.example.viredapp.model.Feed
import com.example.viredapp.model.FeedResult
import com.example.viredapp.services.FeedRepository
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.MyApplication
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FeedBoundaryCallBack(private val cache: FeedLocalCache):PagedList.BoundaryCallback<feed>(){
    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
    }

    override fun onZeroItemsLoaded() {
        Timber.i("onZeroItemsLoaded()")
        requestAndSaveData(0)
    }

    override fun onItemAtEndLoaded(itemAtEnd:feed) {
        Timber.i("onItemAtEndLoaded()")
        var offset:Int = itemAtEnd.auto_id
        requestAndSaveData(offset)
    }

    private fun requestAndSaveData(offset:Int){
        val call: Call<FeedResult> = apiClient.getFeed(NETWORK_PAGE_SIZE,offset)
        call.enqueue(object:Callback<FeedResult>{
            override fun onFailure(call: Call<FeedResult>?, t: Throwable?) {
                Timber.d(t?.message)
            }

            override fun onResponse(call: Call<FeedResult>?, response: Response<FeedResult>?) {
                val res = response?.body()?.feedList
                if (res != null) {
                    cache.insert(res)
                }
            }
        })
    }



}
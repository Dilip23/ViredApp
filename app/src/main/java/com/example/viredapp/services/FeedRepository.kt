package com.example.viredapp.services

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.AsyncTask
import android.util.Log
import com.bumptech.glide.load.engine.Resource
import com.example.viredapp.database.AppLocalCache
import com.example.viredapp.db.FeedDao
import com.example.viredapp.db.feed
import com.example.viredapp.model.Feed
import com.example.viredapp.ui.FeedBoundaryCallBack
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import org.jetbrains.annotations.Async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FeedRepository (
        private val feedDao: FeedDao
){


    private var lastRequestedItem = 1
    private var isRequestInProgress = false


    companion object {
        private const val DEFAULT_NETWORK_PAGE_SIZE = 50
        private const val DEAULT_DATABASE_PAGE_SIZE = 20
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
        // For Singleton instantiation
        @Volatile private var instance: FeedRepository? = null

        fun getInstance(feedDao: FeedDao) =
                instance ?: synchronized(this) {
                    instance ?: FeedRepository(feedDao).also { instance = it }
                }
    }


    fun showFeed():LiveData<PagedList<feed>>{
        Timber.d("showFeed() method called")
        val dataSourceFactory = feedDao.getAllFeed()
        val boundaryCallback = FeedBoundaryCallBack(id = lastRequestedItem)
        val builder = LivePagedListBuilder(dataSourceFactory, DEAULT_DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return builder

    }

    public fun getFeedResponse() {
        if(isRequestInProgress) return
        isRequestInProgress = true
        val call: Call<List<Feed>> = apiClient.getFeed(DEFAULT_NETWORK_PAGE_SIZE, lastRequestedItem)
        call.enqueue(object :Callback<List<Feed>>{
            override fun onFailure(call: Call<List<Feed>>?, t: Throwable?) {
                Timber.d(t.toString())
            }

            override fun onResponse(call: Call<List<Feed>>?, response: Response<List<Feed>>?) {
                if(response!!.isSuccessful){
                    lastRequestedItem += response?.body()!!.size
                    Timber.i("Successful Response -> Adding to DB")
                    addResponseTODB(response.body()!! as List<feed>)
                    isRequestInProgress = false
                }else{
                    when(response.code()){
                        400 -> Timber.d("Not Found 400")
                        500 -> Timber.d("Not logged in or Server broken")
                    }

                }

            }
        })
    }

    private fun addResponseTODB(items:List<feed>) = feedDao.insert(items)

    //public fun loadFromDB():LiveData<PagedList<Feed>> =  feedDao.getAllFeed()


    //TODO:Download Media and Save it in Local Storage

    //TODO:Post User Feed

    //{/feed/{id}}
    //TODO:Update user post
    //fun updatePost()


    //TODO:Delete user post
    //DeletePost





}
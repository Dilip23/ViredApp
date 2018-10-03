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
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * Repository class handling all the operations of User Requests from Network and Database
 * */

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


    /**
     * Retrofit Callback to Server and Delete Record in Database
     * */
    fun sendDeleteRequest(id:Int){
        val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
        val call:Call<ResponseBody> = apiClient.acceptRequest(id)
        call.enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Timber.d(t?.message)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                if (response?.code()!!.equals(204)){
                    cache.acceptRequest(id)
                    Timber.d("Cache Delete Request Performed")
                }
            }
        })

    }

}
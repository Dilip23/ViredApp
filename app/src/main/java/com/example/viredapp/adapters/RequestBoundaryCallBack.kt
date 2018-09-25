package com.example.viredapp.adapters

import android.arch.paging.PagedList
import com.example.viredapp.database.RequestLocalCache
import com.example.viredapp.db.Request
import com.example.viredapp.model.RequestResult
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class RequestBoundaryCallBack(
        private val cache: RequestLocalCache
): PagedList.BoundaryCallback<Request>(){

    companion object {
        private const val NETWORK_PAGE_SIZE = 50
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
    }

    override fun onZeroItemsLoaded() {
        requestAndSaveData(0)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Request) {
        requestAndSaveData(itemAtEnd.id)
    }

    private fun requestAndSaveData(offset:Int){
        val call: Call<RequestResult> = apiClient.getRequests(NETWORK_PAGE_SIZE,offset)
        call.enqueue(object :Callback<RequestResult>{
            override fun onFailure(call: Call<RequestResult>?, t: Throwable?) {
                Timber.d(t?.message)
            }

            override fun onResponse(call: Call<RequestResult>?, response: Response<RequestResult>?) {
                val res = response?.body()?.results
                if (res != null){
                    cache.insert(res)
                }
            }
        })
    }


}
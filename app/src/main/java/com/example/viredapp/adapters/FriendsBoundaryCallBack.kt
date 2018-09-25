package com.example.viredapp.adapters

import android.arch.paging.PagedList
import com.example.viredapp.database.AppLocalCache
import com.example.viredapp.db.Friends
import com.example.viredapp.model.FriendsResult
import com.example.viredapp.model.Result
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class FriendsBoundaryCallBack(
        private val cache:AppLocalCache
):PagedList.BoundaryCallback<Friends>(){

    companion object {
        private const val NETWORK_LIMIT = 50
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
    }

    override fun onZeroItemsLoaded() {
        requestAndSaveData(0)
    }

    override fun onItemAtEndLoaded(itemAtEnd: Friends) {
        requestAndSaveData(itemAtEnd.auto_id)
    }

    private fun requestAndSaveData(offset:Int){
        val call:Call<FriendsResult> = apiClient.getFriends(NETWORK_LIMIT,offset)
        call.enqueue(object :Callback<FriendsResult>{
            override fun onFailure(call: Call<FriendsResult>?, t: Throwable?) {
                Timber.i(t?.message)
            }

            override fun onResponse(call: Call<FriendsResult>?, response: Response<FriendsResult>?) {
                val res = response?.body()?.results
                if (res != null) {
                    cache.insert(res)
                }

            }
        })
    }


}
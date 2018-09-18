package com.example.viredapp.services

import com.example.viredapp.model.Result
import com.example.viredapp.model.UserSearchResult
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class UserRepository {

    val apiClient = ApiClient.getApiClient().create(UserClient::class.java)

    private var lastePage = 1








    /*
    * Network Request to retrieve items
    * */
    fun getUsers(page:Int,query:String){
        var call: Call<UserSearchResult> = apiClient.getUserData(page,query)
        call.enqueue(object :Callback<UserSearchResult>{
            override fun onFailure(call: Call<UserSearchResult>?, t: Throwable?) {
                Timber.d(t?.message.toString())
            }

            override fun onResponse(call: Call<UserSearchResult>?, response: Response<UserSearchResult>?) {
                Timber.d(response?.body().toString())
            }
        })
    }
}
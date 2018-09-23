package com.example.viredapp.adapters

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.example.viredapp.model.Result
import com.example.viredapp.model.UserSearchResult
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executors

class UserDataSource(private var param:String):PageKeyedDataSource<Int,Result>(){
    companion object {
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
        var page = 1
    }
    private var query:String
    var executor = Executors.newSingleThreadExecutor()
    init {
        query = param

    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {
        Timber.d(query+"loadInitial()")
//        val call: Call<UserSearchResult> = apiClient.getUserData(query)
//            call.enqueue(object : Callback<UserSearchResult> {
//                override fun onFailure(call: Call<UserSearchResult>?, t: Throwable?) {
//                    Timber.d("loadInitial() failure")
//                }
//
//                override fun onResponse(call: Call<UserSearchResult>?, response: Response<UserSearchResult>?) {
//                    var user_result = response?.body()
//                    Log.d("loadInitial() --> ",response?.body().toString())
//                    if (user_result == null) {
//                        onFailure(call, HttpException(response))
//                        return
//                    }
//                    callback.onResult(
//                            user_result.results,
//                            0,
//                            user_result.count,
//                            null,
//                            page + 1
//                    )
//                }
//            })
        }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        page = params.key
//        val call:Call<UserSearchResult> = apiClient.getUserData(page,query)
//        call.enqueue(object :Callback<UserSearchResult>{
//            override fun onFailure(call: Call<UserSearchResult>?, t: Throwable?) {
//                Timber.d("loadAfter() Failure")
//            }
//
//            override fun onResponse(call: Call<UserSearchResult>?, response: Response<UserSearchResult>?) {
//                var user_result = response?.body()
//                Log.d("loadAfter() --> ",response?.body().toString())
//                if(user_result == null){
//                    onFailure(call,HttpException(response))
//                    return
//                }
//                callback.onResult(
//                        user_result.results,
//                        page+1
//                )
//            }
//        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        /*
        * We only append data after retrieving
        * */
    }

}


package com.example.viredapp.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.viredapp.adapters.UserAdapter
import com.example.viredapp.adapters.UserDataSource
import com.example.viredapp.adapters.UserDataSourceFactory
import com.example.viredapp.model.Result
import com.example.viredapp.model.UserSearchResult
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.MyApplication
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executors

class UserRepository {

    val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
    val executors = Executors.newSingleThreadExecutor()
    private var lastePage = 1


    /**
     * Code for @see{DataSource} and @see{LivePagedListBuilder} and @see{PageConfig}
     * */


    fun showUsers(query: String):LiveData<PagedList<Result>>{
            val userDataSourceFactory = UserDataSourceFactory(query)
            val pagedListConfig: PagedList.Config =
                    PagedList.Config.Builder()
                            .setEnablePlaceholders(false)
                            .setPageSize(UserDataSource.page)
                            .build()
            return LivePagedListBuilder(userDataSourceFactory, pagedListConfig)
                    .setFetchExecutor(executors)
                    .build()

    }


    /*
    * Network Request to retrieve items
    * @sample{function}
    * @see{UserdataSource}
    * */

//    fun getUsers(page:Int,query:String) {
//            var call: Call<Result> = apiClient.getUserData(query)
//            call.enqueue(object : Callback<Result> {
//                override fun onFailure(call: Call<UserSearchResult>?, t: Throwable?) {
//                    Timber.d(t?.message.toString())
//                }
//
//                override fun onResponse(call: Call<UserSearchResult>?, response: Response<UserSearchResult>?) {
//                    Timber.d("onResponse() -Rachel  Rachel-->" + response?.body().toString())
//                }
//            })
//        }
//
//    fun searchUsers(query: String,adapter: UserAdapter,listView:ListView): List<Result>? {
//        var resultList: List<Result>?
//        val call:Call<List<Result>> = apiClient.getUserData(query)
//        call.enqueue(object :Callback<List<Result>>{
//            override fun onFailure(call: Call<List<Result>>?, t: Throwable?) {
//                Toast.makeText(MyApplication.getContext(),t?.message,Toast.LENGTH_LONG).show()
//                Timber.i(t?.message)
//            }
//
//            override fun onResponse(call: Call<List<Result>>?, response: Response<List<Result>>?) {
//                resultList = response?.body()
//                Timber.i(response?.body().toString())
//                Timber.i(resultList.toString())
//
//            }
//        })
//        return resultList
    }



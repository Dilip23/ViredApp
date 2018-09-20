package com.example.viredapp.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.support.annotation.MainThread
import com.example.viredapp.adapters.UserDataSource
import com.example.viredapp.adapters.UserDataSourceFactory
import com.example.viredapp.model.Result
import com.example.viredapp.model.UserSearchResult
import com.example.viredapp.utilities.ApiClient
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

    fun getUsers(page:Int,query:String) {
            var call: Call<UserSearchResult> = apiClient.getUserData(page, query)
            call.enqueue(object : Callback<UserSearchResult> {
                override fun onFailure(call: Call<UserSearchResult>?, t: Throwable?) {
                    Timber.d(t?.message.toString())
                }

                override fun onResponse(call: Call<UserSearchResult>?, response: Response<UserSearchResult>?) {
                    Timber.d("onResponse() -Rachel  Rachel-->" + response?.body().toString())
                }
            })
        }
}

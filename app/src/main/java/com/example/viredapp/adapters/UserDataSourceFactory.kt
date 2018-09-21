package com.example.viredapp.adapters

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.example.viredapp.model.Result
import timber.log.Timber

public class UserDataSourceFactory(param:String) : DataSource.Factory<Int, Result>() {
    private var query:String
    val sourceLiveData = MutableLiveData<UserDataSource>()
    init {
        query  = param
    }

    override fun create(): DataSource<Int, Result> {
        Timber.d("UserDataSourcefactory create()")
        val source:UserDataSource = UserDataSource(query)
        sourceLiveData.postValue(source)
        return source
    }



}
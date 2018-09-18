package com.example.viredapp.adapters

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.example.viredapp.model.Result

public class UserDataSourceFactory(param:String) : DataSource.Factory<Int, Result>() {
    private var query:String
    private var resultLiveDataSource: MutableLiveData<PageKeyedDataSource<Int,Result>> = MutableLiveData()

    init {
        query  = param
    }

    override fun create(): DataSource<Int, Result> {
        var userDataSource:UserDataSource = UserDataSource(query)
        resultLiveDataSource.postValue(userDataSource)

        return userDataSource
    }


    public fun getResultLiveDataSource():MutableLiveData<PageKeyedDataSource<Int,Result>>{
        return resultLiveDataSource
    }


}
package com.example.viredapp.adapters

import android.arch.paging.PageKeyedDataSource
import com.example.viredapp.model.Result

class UserDataSource():PageKeyedDataSource<Int,Result>(){
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Result>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {

    }

}
package com.example.viredapp.model

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import android.os.AsyncTask
import com.example.viredapp.adapters.UserDataSource
import com.example.viredapp.adapters.UserDataSourceFactory
import com.example.viredapp.services.UserRepository
import java.util.concurrent.Executors

class UsersViewModel internal constructor(private val repository: UserRepository): ViewModel() {
    private val searchQuery = MutableLiveData<String>()
    private val userResult = map(searchQuery) {
        repository.showUsers(it)
    }
    val items = switchMap(userResult) { it.value as LiveData<PagedList<Result>> }
    fun showSearchResults(searchQuery: String): Boolean {
        if (this.searchQuery.value == searchQuery) {
            return false
        }
        this.searchQuery.value = searchQuery
        return true
    }


    fun showResult(searchQuery: String): LiveData<PagedList<Result>> {
                return repository.showUsers(searchQuery)
    }


}

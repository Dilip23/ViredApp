package com.example.viredapp.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.PagedList
import com.example.viredapp.adapters.UserDataSource
import com.example.viredapp.adapters.UserDataSourceFactory

class UsersViewModel(param:String) : ViewModel() {
    var resultPagedList:LiveData<PagedList<Result>>
    var liveDataSource:LiveData<PageKeyedDataSource<Int,Result>>
    private var query:String

    init {
        query = param
        val userDataSourceFactory = UserDataSourceFactory(query)
        liveDataSource  = userDataSourceFactory.getResultLiveDataSource()

        val pagedListConfig : PagedList.Config =
                PagedList.Config.Builder()
                        .setEnablePlaceholders(false)
                        .setPageSize(UserDataSource.page)
                        .build()
        resultPagedList = LivePagedListBuilder(userDataSourceFactory,pagedListConfig).build()
    }

    fun invalidateData(query:String) = UserDataSource(query).invalidate()

}

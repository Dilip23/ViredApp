package com.example.viredapp.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.viredapp.adapters.FriendsBoundaryCallBack
import com.example.viredapp.database.AppLocalCache
import com.example.viredapp.db.Friends

class FriendsRepository (private val cache: AppLocalCache){

    companion object {
        private const val DATABASE_PAGE_SIZE = 20
    }


    fun showFriends():LiveData<PagedList<Friends>>{
        val dataSourceFactory = cache.getAllFeed()
        val boundaryCallBack = FriendsBoundaryCallBack(cache)
        val data = LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE)
                .setBoundaryCallback(boundaryCallBack)
                .build()

        return data
    }


}
package com.example.viredapp.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.DataSource
import com.example.viredapp.db.FeedDao
import com.example.viredapp.db.feed
import com.example.viredapp.model.Feed
import timber.log.Timber
import java.util.concurrent.Executor


class AppLocalCache(
        private val feedDao: FeedDao,
        private val ioExecutor: Executor
){
    fun insert(feed:List<feed>){
        ioExecutor.execute {
            Timber.d("inserting ${feed.size} feeds")
            feedDao.insert(feed)
        }
    }

    fun getAllFeed(): DataSource.Factory<Int,feed> {
        return feedDao.getAllFeed()
    }

}
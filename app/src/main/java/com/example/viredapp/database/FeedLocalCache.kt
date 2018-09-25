package com.example.viredapp.database

import android.arch.paging.DataSource
import com.example.viredapp.db.FeedDao
import com.example.viredapp.db.feed
import com.example.viredapp.model.Feed
import timber.log.Timber
import java.util.concurrent.Executor

class FeedLocalCache(
        private val feedDao: FeedDao,
        private val ioExecutor: Executor
){

    fun insert(feed:feed){
        ioExecutor.execute {
            Timber.d("inserting ${feed} feedItem")
            feedDao.insert(feed)
        }
    }

    fun getAllFeed():DataSource.Factory<Int,feed>{
        return feedDao.getAllFeed()
    }

}
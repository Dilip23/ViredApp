package com.example.viredapp.database

import android.arch.paging.DataSource
import com.example.viredapp.db.FeedDao
import com.example.viredapp.db.feed
import timber.log.Timber
import java.util.concurrent.Executor


class AppLocalCache(
        private val feedDao: FeedDao,
        private val ioExecutor: Executor
){
    fun insert(feed: feed){
        ioExecutor.execute {
            Timber.d("inserting ${feed} feeds")
            feedDao.insert(feed)
        }
    }

    fun getAllFeed(): DataSource.Factory<Int,feed> {
        return feedDao.getAllFeed()
    }

}
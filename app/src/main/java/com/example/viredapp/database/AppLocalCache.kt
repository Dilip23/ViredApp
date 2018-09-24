package com.example.viredapp.database

import android.arch.paging.DataSource
import com.example.viredapp.db.FeedDao
import com.example.viredapp.db.Friends
import com.example.viredapp.db.FriendsDao
import com.example.viredapp.db.feed
import timber.log.Timber
import java.util.concurrent.Executor


class AppLocalCache(
        private val friendsDao: FriendsDao,
        private val ioExecutor: Executor
){
    fun insert(friends: List<Friends>){
        ioExecutor.execute {
            Timber.d("inserting ${friends} feeds")
            friendsDao.insert(friends)
        }
    }

    fun getAllFeed(): DataSource.Factory<Int,Friends> {
        return friendsDao.showFriends()
    }

}
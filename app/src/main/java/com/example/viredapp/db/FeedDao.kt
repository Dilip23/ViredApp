package com.example.viredapp.db

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.persistence.room.*
import com.example.viredapp.model.PostFeed

@Dao
interface FeedDao{

    //Post feed item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(feed:List<feed>)

    //Update feed item
    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(feedItem:feed)

    //Get feed from user friend's
    @Query("SELECT * from feed")
    fun getAllFeed():DataSource.Factory<Int,feed>

}
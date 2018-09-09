package com.example.viredapp.db

import android.arch.paging.DataSource
import android.arch.persistence.room.*


@Dao
interface FeedDao{

    //Post feed item
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(feed:feed):Long

    //Update feed item
    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(feedItem:feed)

    //Get feed from user friend's
    @Query("SELECT * from feed")
    fun getAllFeed():DataSource.Factory<Int,feed>

}
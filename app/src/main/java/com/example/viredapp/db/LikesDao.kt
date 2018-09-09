package com.example.viredapp.db

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update


@Dao
public interface LikesDao {

    //Get likes to display under feed
    @Query("SELECT * from Likes "+"INNER JOIN feed ON feed.id = Likes.media_id")
    fun getLikesForFeed():LiveData<PagedList<Likes>>

    //TODO:Get Likes done by users for a feed item


}
package com.example.viredapp.db

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.persistence.room.*


@Dao
public interface RequestDao{

    @Query("SELECT * from Request")
    fun showPendingRequests():DataSource.Factory<Int,Request>

    //Send Request to a user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(request: List<Request>)

    //Accept request from a user
    @Delete
    fun acceptRequest(request: Request)

    //Change request to Spam
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun spamRequest(request: Request)

}
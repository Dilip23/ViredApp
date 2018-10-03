package com.example.viredapp.db

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.paging.PagedList
import android.arch.persistence.room.*

/**
 * Dao methods to handle Request Operations between Users
 * */


@Dao
public interface RequestDao{


    //Showing pending Requests sent to User
    @Query("SELECT * from Request")
    fun showPendingRequests():DataSource.Factory<Int,Request>

    //Insert Request to a user into Database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(request: List<Request>)

    //Accept request from a user
    @Query("DELETE FROM Request WHERE id = :id")
    fun acceptRequest(id:Int)

}
package com.example.viredapp.db

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import android.arch.persistence.room.*


@Dao
public interface RequestDao{
    //Show pending Requests
    //@Query("SELECT * from Request")
    //fun showPendingRequests():LiveData<PagedList<Request>>

    //TODO:Handle Requests
    //Send Request to a user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun sendRequest(request: Request)

    //Accept request from a user
    @Delete
    fun acceptRequest(request: Request)

    //Reject a request from user
    @Delete
    fun rejectRequest(request: Request)
}
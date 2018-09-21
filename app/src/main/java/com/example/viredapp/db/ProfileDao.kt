package com.example.viredapp.db

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.viredapp.model.Result

@Dao
public interface ProfileDao {

    //Show User Profile
    @Query("SELECT * from PROFILE")
    fun getProfile():DataSource.Factory<Int,Result>

    //Update User Profile
    @Update
    fun updateProfile(profile:Profile)


}
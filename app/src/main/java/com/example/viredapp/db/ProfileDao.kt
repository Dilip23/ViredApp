package com.example.viredapp.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
public interface ProfileDao {

    //Show User Profile
    @Query("SELECT * from PROFILE")
    fun getProfile():LiveData<Profile>

    //Update User Profile
    @Update
    fun updateProfile(profile:Profile)


}
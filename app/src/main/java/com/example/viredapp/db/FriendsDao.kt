package com.example.viredapp.db

import android.arch.paging.DataSource
import android.arch.persistence.room.*

@Dao
public interface FriendsDao {

    //Insert friends
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(friends: List<Friends>)


    //Show Friends List
    @Query("SELECT * FROM FRIENDS")
    fun showFriends():DataSource.Factory<Int,Friends>
}
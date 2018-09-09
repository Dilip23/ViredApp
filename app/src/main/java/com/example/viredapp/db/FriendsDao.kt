package com.example.viredapp.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Query

@Dao
public interface FriendsDao {

    //Show Friends List
    @Query("SELECT * FROM FRIENDS")

    //Show Friendship Profile

    //Delete Friendship
    @Delete
    fun deleteFriendship(friends: Friends)
}
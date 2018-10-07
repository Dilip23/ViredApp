package com.example.viredapp.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList
import com.example.viredapp.db.Friends
import com.example.viredapp.services.FriendsRepository

class FriendsViewModel(private val repository: FriendsRepository) : ViewModel() {

    fun observeFriends():LiveData<PagedList<Friends>> = repository.showFriends()

    fun getFriend(name:String): LiveData<Friends> = repository.getFriend(name)

}

package com.example.viredapp.model
import android.arch.lifecycle.*
import android.arch.paging.PagedList
import com.example.viredapp.db.feed
import com.example.viredapp.services.FeedRepository



class FeedViewModel internal constructor(private val feedRepository: FeedRepository):ViewModel() {

    // TODO: Implement the ViewModel



    fun showFeed():LiveData<PagedList<feed>> = feedRepository.showFeed()
}
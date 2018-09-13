package com.example.viredapp.services

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.example.viredapp.db.FeedDao
import com.example.viredapp.db.feed
import com.example.viredapp.model.Feed
import com.example.viredapp.model.FeedResult
import com.example.viredapp.adapters.FeedBoundaryCallBack
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executors


class FeedRepository (
        private val feedDao: FeedDao
){


    private var lastRequestedItem = 0
    private var isRequestInProgress = false
    var executor = Executors.newSingleThreadExecutor()
    companion object {
        lateinit var feedList:List<Feed>
        private const val DEFAULT_NETWORK_PAGE_SIZE = 50
        private const val DEAULT_DATABASE_PAGE_SIZE = 20
        private val apiClient = ApiClient.getApiClient().create(UserClient::class.java)
        // For Singleton instantiation
        @Volatile private var instance: FeedRepository? = null

        fun getInstance(feedDao: FeedDao) =
                instance ?: synchronized(this) {
                    instance ?: FeedRepository(feedDao).also { instance = it }
                }
    }


    fun showFeed():LiveData<PagedList<feed>>{
        Timber.d("showFeed() method called")
        val dataSourceFactory = feedDao.getAllFeed()
        val boundaryCallback = FeedBoundaryCallBack(id = lastRequestedItem)
        val config = PagedList.Config.Builder()
                .setPageSize(DEAULT_DATABASE_PAGE_SIZE)
                .build()
        val builder = LivePagedListBuilder(dataSourceFactory, config)
                .setBoundaryCallback(boundaryCallback)
                .build()
        return builder
    }

    public fun getFeedResponse() {
        Timber.i("getFeedResponse()")
        if(isRequestInProgress) return
        isRequestInProgress = true
        val call: Call<FeedResult> = apiClient.getFeed(DEFAULT_NETWORK_PAGE_SIZE,lastRequestedItem)
        call.enqueue(object :Callback<FeedResult>{
            override fun onFailure(call: Call<FeedResult>?, t: Throwable?) {
                Timber.d(t.toString())
            }

            override fun onResponse(call: Call<FeedResult>?, response: Response<FeedResult>?) {
                if(response!!.isSuccessful){
                    //lastRequestedItem += response?.body()!!.size
                    feedList = response?.body()!!.feedList
                    for (i in feedList){
                        var name = i.username
                        var media = i.mUrl
                        var loc = i.location
                        var like = i.likesCount.toString()
                        var timeStamp = i.timeStamp
                        var id = i.id.toLong()
                        var res:feed = feed(id,name,media,timeStamp,loc,like)
                        var check_r =  addResponseTODB(res)
                        Timber.d("$check_r")
                    }
                    Timber.i("Successful Response -> Adding to DB")
                    isRequestInProgress = false
                }else{
                    when(response.code()){
                        400 -> Timber.d("Not Found 400")
                        500 -> Timber.d("Not logged in or Server broken")
                    }

                }

            }
        })
    }

    private fun addResponseTODB(items:feed):Long{
        var r:Long = 0
        executor.execute( {
           var c_r =  feedDao.insert(items)
            Timber.d(r.toString())
            r = c_r
        })
        Timber.d("Feed object Inserted into Database")
        return r
    }

    //public fun loadFromDB():LiveData<PagedList<feed>> =  feedDao.getAllFeed()


    //TODO:Download Media and Save it in Local Storage

    //TODO:Post User feed


}

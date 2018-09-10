package com.example.viredapp.ui


import android.arch.paging.PagedListAdapter
import android.content.Context
import android.net.Uri
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.viredapp.R
import com.example.viredapp.db.feed
import kotlinx.android.synthetic.main.feed_fragment.view.*

import kotlinx.android.synthetic.main.feedrow.view.*

class FeedAdapter(val context:Context) : PagedListAdapter<feed,FeedAdapter.ViewHolder>(FeedDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val userPost = LayoutInflater.from(parent.context)
                        .inflate(R.layout.feedrow,parent,false)
            return ViewHolder(userPost)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedItem = getItem(position)
        if(feedItem != null){
            holder.bind(feedItem)
        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        //Retrieve data
        private val username:TextView = itemView.post_name

        private val userPic:ImageView = itemView.profileImage
        private val location:TextView = itemView.postLocation
        private val time:TextView = itemView.postTime
        private val post:ImageView = itemView.postImage

        fun bind(feed: feed) = with(itemView){
            //TODO:Bind Data with View
                showFeedData(feed)

        }

        private fun showFeedData(feed: feed) {
            username.text = feed.username
            userPic.setImageURI(null)
            userPic.visibility = View.GONE
            location.text = feed.location
            time.text = feed.timeStamp.toString()
            post.setImageURI(Uri.parse(feed.mUrl))

        }

    }

}

class FeedDiffCallBack : DiffUtil.ItemCallback<feed>() {
    override fun areItemsTheSame(oldItem:feed, newItem: feed): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: feed, newItem: feed): Boolean {
        return oldItem == newItem
    }

}

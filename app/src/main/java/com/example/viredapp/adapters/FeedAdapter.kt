package com.example.viredapp.adapters


import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.viredapp.R
import com.example.viredapp.db.feed

import kotlinx.android.synthetic.main.feedrow.view.*

class FeedAdapter(val context:Context) : PagedListAdapter<feed, FeedAdapter.MyViewHolder>(FeedDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val userPost = LayoutInflater.from(parent.context)
                        .inflate(R.layout.feedrow,parent,false)
            return MyViewHolder(userPost)
    }



    override fun onBindViewHolder(holderMy: MyViewHolder, position: Int) {
        val feedItem = getItem(holderMy.adapterPosition)
        if(feedItem != null){
            holderMy.bind(feedItem)
        }
    }

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        //Retrieve data
        private val username:TextView = itemView.post_name

        private val userPic:ImageView = itemView.profileImage
        private val location:TextView = itemView.postLocation
        private val time:TextView = itemView.postTime
        private val post:ImageView = itemView.postImage

        fun bind(feed: feed) = with(itemView){
                showFeedData(feed)

        }

        private fun showFeedData(feed: feed) {
            username.text = feed.username
            userPic.setImageURI(null)
            userPic.visibility = View.GONE
            location.text = feed.location
            time.text = feed.timeStamp.toString()

            //Loading Feed post Image
            Glide
                    .with(post)
                    .load(feed.mUrl)
                    .into(post.postImage)

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

package com.example.viredapp.ui


import android.arch.paging.PagedListAdapter
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
import com.example.viredapp.model.Feed

class FeedAdapter : PagedListAdapter<Feed,FeedAdapter.ViewHolder>(FeedDiffCallBack()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.feedrow,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feedItem = getItem(position)
        if(feedItem != null){
            holder.bind(feedItem)
        }
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        //Retrieve data
        private val username:TextView = itemView.findViewById(R.id.username_txt)
        private val userPic:ImageView = itemView.findViewById(R.id.feedImage)
        private val location:TextView = itemView.findViewById(R.id.postLocation)
        private val time:TextView = itemView.findViewById(R.id.postTime)
        private val post:ImageView = itemView.findViewById(R.id.postImage)

        fun bind(feed: Feed?) = with(itemView){
            //TODO:Bind Data with View
            if(feed == null) {
                post.visibility = View.GONE
            }
            else{
                showFeedData(feed)
            }

        }

        private fun showFeedData(feed: Feed) {
            username.text = feed.username
            userPic.setImageURI(null)
            userPic.visibility = View.GONE
            location.text = feed.location
            time.text = feed.timeStamp
            post.setImageURI(Uri.parse(feed.m_url))

        }

    }

}

class FeedDiffCallBack : DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed?, newItem: Feed?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Feed?, newItem: Feed?): Boolean {
        return oldItem == newItem
    }

}

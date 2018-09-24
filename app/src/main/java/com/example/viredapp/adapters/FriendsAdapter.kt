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
import com.example.viredapp.R
import com.example.viredapp.db.Friends

class FriendsAdapter(val context: Context):PagedListAdapter<Friends,FriendsAdapter.MyViewHolder>(FriendsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val friendsItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_row,parent,false)
        return MyViewHolder(friendsItem)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val friendsItem = getItem(holder.adapterPosition)
        if (friendsItem!= null){
            holder.bind(friendsItem)
        }

    }


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        private val username:TextView = itemView.findViewById(R.id.username)
        private val userImage:ImageView = itemView.findViewById(R.id.userImage)

        fun bind(friends: Friends) = with(itemView){
            showFriendsData(friends)
        }

        private fun showFriendsData(friends: Friends){
            username.text = friends.friend_id
            userImage.visibility = View.GONE

        }

    }
}

class FriendsDiffUtil : DiffUtil.ItemCallback<Friends>() {


    override fun areItemsTheSame(oldItem: Friends?, newItem: Friends?): Boolean {
        return oldItem?.id == newItem?.id
    }


    override fun areContentsTheSame(oldItem: Friends?, newItem: Friends?): Boolean {
        return oldItem == newItem
    }

}

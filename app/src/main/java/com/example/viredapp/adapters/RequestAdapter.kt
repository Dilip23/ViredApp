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
import com.example.viredapp.db.Request

class RequestAdapter(val context: Context):PagedListAdapter<Request,RequestAdapter.MyViewHolder>(RequestDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val requestItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_row,parent,false)
        return MyViewHolder(requestItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val requestItem = getItem(holder.adapterPosition)
        if (requestItem!=null){
            holder.bind(requestItem)
        }
    }

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        private val userName:TextView = itemView.findViewById(R.id.username)
        private val userImage:ImageView = itemView.findViewById(R.id.userImage)

        fun bind(request: Request) = with(itemView){
            showRequestData(request)
        }

        private fun showRequestData(request: Request){
            userName.text = request.user_id.toString()
            userImage.visibility = View.GONE
        }
    }
}

class RequestDiffUtil:DiffUtil.ItemCallback<Request>() {
    override fun areItemsTheSame(oldItem: Request?, newItem: Request?): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Request?, newItem: Request?): Boolean {
        return oldItem == newItem
    }

}

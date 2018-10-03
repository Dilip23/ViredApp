package com.example.viredapp.adapters

import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.viredapp.R
import com.example.viredapp.database.AppDatabase
import com.example.viredapp.database.RequestLocalCache
import com.example.viredapp.db.Request
import com.example.viredapp.db.RequestDao
import com.example.viredapp.services.RequestRepository
import com.example.viredapp.ui.RequestFragment
import com.example.viredapp.utilities.InjectorUtils
import com.example.viredapp.utilities.ItemClickListener
import com.example.viredapp.utilities.MyApplication
import kotlinx.android.synthetic.main.request_item.view.*
import kotlin.coroutines.experimental.coroutineContext


/**
 *  Adapter class for Request Recycler View
 * */

class RequestAdapter(val context: Context):PagedListAdapter<Request,RequestAdapter.MyViewHolder>(RequestDiffUtil()) {

    lateinit var mitemClickListener:ItemClickListener

    public fun setOnItemClickListener(itemClickListener: ItemClickListener){
         mitemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val requestItem = LayoutInflater.from(parent.context)
                .inflate(R.layout.request_item,parent,false)
        return MyViewHolder(requestItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val requestItem = getItem(holder.adapterPosition)
        if (requestItem!=null){
            holder.bind(requestItem)
        }

      holder.itemView.accept.setOnClickListener(object: View.OnClickListener{
          override fun onClick(v: View?) {
              if (mitemClickListener != null){
                  val id = requestItem?.id!!
                  val position = holder.adapterPosition
                  mitemClickListener.onDeleteClick(id,position)
              }
          }
      })

    }


    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        private val userName:TextView = itemView.findViewById(R.id.username)
        private val accept:Button = itemView.findViewById(R.id.accept)

        fun bind(request: Request) = with(itemView){
            showRequestData(request)
        }

        private fun showRequestData(request: Request) {
            userName.text = request.user_id.toString()
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

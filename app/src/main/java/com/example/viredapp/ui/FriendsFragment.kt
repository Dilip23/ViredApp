package com.example.viredapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.viredapp.R
import com.example.viredapp.adapters.FriendsAdapter
import com.example.viredapp.db.Friends
import com.example.viredapp.model.FriendsViewModel
import com.example.viredapp.utilities.InjectorUtils
import com.example.viredapp.utilities.MyApplication

class FriendsFragment : Fragment() {

    companion object {
        fun newInstance() = FriendsFragment()
    }

    private lateinit var viewModel: FriendsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.friends_fragment, container, false)
        val context = context ?: return view
        val adapter = FriendsAdapter(context)
        view.findViewById<RecyclerView>(R.id.friendsRecyclerView).adapter = adapter
        view.findViewById<RecyclerView>(R.id.friendsRecyclerView).layoutManager = LinearLayoutManager(MyApplication.getContext())
        subscribeUI(adapter,context)

        return view

    }

    private fun subscribeUI(adapter: FriendsAdapter, context: Context) {
        val factory = InjectorUtils.provideFriendsViewModel(context)
        viewModel = ViewModelProviders.of(this,factory).get(FriendsViewModel::class.java)
        viewModel.observeFriends().observe(this,object :Observer<PagedList<Friends>>{
            override fun onChanged(t: PagedList<Friends>?) {
                adapter.submitList(t)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FriendsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

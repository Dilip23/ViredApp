package com.example.viredapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
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
import android.widget.ListView

import com.example.viredapp.R
import com.example.viredapp.adapters.RequestAdapter
import com.example.viredapp.db.Request
import com.example.viredapp.model.RequestViewModel
import com.example.viredapp.utilities.InjectorUtils
import com.example.viredapp.utilities.MyApplication
import java.util.concurrent.Executors

class RequestFragment : Fragment() {

    companion object {
        fun newInstance() = RequestFragment()
    }

    private lateinit var viewModel: RequestViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.request_fragment, container, false)
        val context = context?:return  view
        val adapter = RequestAdapter(context)
        view.findViewById<RecyclerView>(R.id.requestRecyclerView).adapter = adapter
        view.findViewById<RecyclerView>(R.id.requestRecyclerView).layoutManager = LinearLayoutManager(MyApplication.getContext())
        subscribeUI(adapter,context)
        return view
    }

    private fun subscribeUI(adapter: RequestAdapter, context: Context) {
        val factory = InjectorUtils.provideRequestViewModel(context)
        viewModel = ViewModelProviders.of(this,factory).get(RequestViewModel::class.java)
        viewModel.observeRequests().observe(this,object:Observer<PagedList<Request>>{
            override fun onChanged(t: PagedList<Request>?) {
                adapter.submitList(t)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RequestViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

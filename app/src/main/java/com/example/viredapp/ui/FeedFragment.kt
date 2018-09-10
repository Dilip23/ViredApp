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
import com.example.viredapp.model.FeedViewModel
import com.example.viredapp.R
import com.example.viredapp.db.feed
import com.example.viredapp.utilities.InjectorUtils
import com.example.viredapp.utilities.MyApplication
import kotlinx.android.synthetic.main.feed_fragment.*


class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private lateinit var viewModel: FeedViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.feed_fragment, container, false)
        val context = getContext() ?: return view
        val adapter = FeedAdapter(context)
        view.findViewById<RecyclerView>(R.id.feedView).adapter = adapter
        view.findViewById<RecyclerView>(R.id.feedView).layoutManager = LinearLayoutManager(MyApplication.getContext())
        subscribeUI(adapter,context)


        return view
    }

    private fun subscribeUI(adapter: FeedAdapter,context:Context) {
        val factory = InjectorUtils.provideViewModelFactory(context)
        viewModel = ViewModelProviders.of(this,factory).get(FeedViewModel::class.java)
        viewModel.showFeed().observe(this, object:Observer<PagedList<feed>>{
            override fun onChanged(t: PagedList<feed>?) {
                adapter.submitList(t)
                adapter.notifyDataSetChanged()
            }

        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}

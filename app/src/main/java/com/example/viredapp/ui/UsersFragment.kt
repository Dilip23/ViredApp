package com.example.viredapp.ui

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.SearchView

import com.example.viredapp.R
import com.example.viredapp.adapters.UserAdapter
import com.example.viredapp.model.Result
import com.example.viredapp.model.UserSearchResult
import com.example.viredapp.model.UserViewModelFactory
import com.example.viredapp.model.UsersViewModel
import com.example.viredapp.services.UserRepository
import com.example.viredapp.utilities.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executors

class UsersFragment : Fragment() {

    companion object {
        fun newInstance() = UsersFragment()
        val userClient = ApiClient.getApiClient().create(UserClient::class.java)
    }

    private lateinit var viewModel: UsersViewModel
    private var searchView:SearchView? = null
    private lateinit var rec_view:RecyclerView
    val executor = Executors.newSingleThreadExecutor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.users_fragment, container, false)
        val context = getContext() ?: return view
        val adapter = UserAdapter(context)
        rec_view = view.findViewById<RecyclerView>(R.id.usersRecycler)
        rec_view.layoutManager = LinearLayoutManager(MyApplication.getContext())
        viewModel = viewModel()
        initAdapter()
        return  view
    }

    private fun initAdapter() {
        val adapter = UserAdapter(context)
        rec_view.adapter = adapter
        viewModel.items.observe(this,Observer<PagedList<Result>>{
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun viewModel():UsersViewModel {
        val viewModelFactory = InjectorUtils.provideUserViewModel(context!!)
        return ViewModelProviders.of(this,viewModelFactory)[UsersViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.options_menu,menu)
        var searchItem:MenuItem = menu!!.findItem(R.id.search)
        var searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = SearchView((context as FeedActivity).supportActionBar?.themedContext ?: context)
        searchItem.apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = searchView
        }

//        searchView = searchItem.actionView as SearchView
        if(searchView != null){

            searchView!!.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchNetwork(query)
                    Timber.d(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    /*
                    * Do Nothing
                    * */
                    return true
                }
            })

        }

    }
    private fun searchNetwork(searchQuery:String){
            searchQuery.trim().let {
            if (it.isNotEmpty()){
                executor.execute {
                    viewModel.showResult(searchQuery)
                }
                rec_view.adapter.notifyDataSetChanged()
            }
        }

    }
}
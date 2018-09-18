package com.example.viredapp.ui

import android.app.SearchManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.SearchView

import com.example.viredapp.R
import com.example.viredapp.model.UserSearchResult
import com.example.viredapp.model.UsersViewModel
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.PrefConfig
import com.example.viredapp.utilities.UserClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class UsersFragment : Fragment() {

    companion object {
        fun newInstance() = UsersFragment()
        val userClient = ApiClient.getApiClient().create(UserClient::class.java)
    }

    private lateinit var viewModel: UsersViewModel
    private var searchView:SearchView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.users_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        // TODO: Use the ViewModel
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
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    Timber.d(query)
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    Timber.d(newText)
                }
            })

        }

    }
}
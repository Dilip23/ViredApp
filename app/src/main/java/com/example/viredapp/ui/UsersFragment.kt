package com.example.viredapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.AdapterView
import android.widget.ListView

import com.example.viredapp.R
import com.example.viredapp.adapters.UserAdapter
import com.example.viredapp.model.Result
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
    private var searchView: SearchView? = null
    private lateinit var listView: ListView
    val executor = Executors.newSingleThreadExecutor()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.users_fragment, container, false)
        val context = getContext() ?: return view
        listView = view.findViewById(R.id.listView)
        //viewModel = viewModel()
        
        listView.setOnItemClickListener(object :AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val intent = Intent(context,UserActivity::class.java)
                startActivity(intent)
            }
        })

        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.options_menu, menu)
        var searchView: SearchView? = menu!!.findItem(R.id.search).actionView as SearchView?
        var searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
       // searchView = SearchView((context as FeedActivity).supportActionBar?.themedContext
        //        ?: context)
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        searchView!!.isSubmitButtonEnabled
        searchUser(searchView)
        /*
        searchItem.apply {
            setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        searchView = searchItem.actionView as SearchView
        */
    }

    private fun searchUser(searchView: SearchView?) {
        searchView!!.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val userClient = ApiClient.getApiClient().create(UserClient::class.java)
                val call:Call<List<Result>> = userClient.getUserData(query)
                call.enqueue(object:Callback<List<Result>>{
                    override fun onFailure(call: Call<List<Result>>?, t: Throwable?) {
                        Timber.i(t?.message)
                    }

                    override fun onResponse(call: Call<List<Result>>?, response: Response<List<Result>>?) {
                        Timber.i(response?.body().toString())
                        var resultList:List<Result> = response?.body()!!
                        /*
                        * Set Data to ListView adapter
                        * */
                        val userAdapter = UserAdapter(MyApplication.getContext(),resultList)
                        listView.adapter = userAdapter
                    }
                })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
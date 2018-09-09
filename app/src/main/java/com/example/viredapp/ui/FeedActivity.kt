package com.example.viredapp.ui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.navigateUp
import com.example.viredapp.R
import com.example.viredapp.model.Feed
import com.example.viredapp.model.FeedViewModel
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.UserClient
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedActivity : AppCompatActivity() {
    private var bottomLayout:BottomNavigationViewEx? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val host = supportFragmentManager.findFragmentById(R.id.feed_activity_host_nav_fragment) as NavHostFragment //?: return
        val navController = host.navController
        
        //Set BottomNavigation View
        setUpBottomNavMenu(navController)
        
        val bnve:BottomNavigationViewEx = findViewById(R.id.bottom_nav)
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        bnve.let { bottomNavView -> NavigationUI.setupWithNavController(bottomNavView,navController) }

    }

    private fun setUpBottomNavMenu(navController: NavController) {
        findViewById<BottomNavigationViewEx>(R.id.bottom_nav)?.let { navigationView ->
            NavigationUI.setupWithNavController(navigationView,navController)
        }

    }



}

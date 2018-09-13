package com.example.viredapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.viredapp.R
import com.example.viredapp.utilities.MyApplication
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    private var bottomLayout:BottomNavigationViewEx? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val host = supportFragmentManager.findFragmentById(R.id.feed_activity_host_nav_fragment) as NavHostFragment //?: return
        val navController = host.navController
        
        //Set BottomNavigation View
        setUpBottomNavMenu(navController)
        
        val bnve:BottomNavigationViewEx = this.findViewById(R.id.bottom_nav)
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

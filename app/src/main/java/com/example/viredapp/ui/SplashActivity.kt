package com.example.viredapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.IntegerRes
import com.example.viredapp.R

class SplashActivity : AppCompatActivity() {

    companion object {
         var SPLASH_TIME_OUT:Long = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable{
            kotlin.run {
                val i:Intent = Intent(this,MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }, SPLASH_TIME_OUT)


    }
}

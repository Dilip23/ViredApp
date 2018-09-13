package com.example.viredapp.ui

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import com.example.viredapp.R
import android.widget.Toast
import com.example.viredapp.db.FeedDao
import com.example.viredapp.model.*
import com.example.viredapp.services.FeedRepository
import com.example.viredapp.utilities.PrefConfig
import com.example.viredapp.utilities.UserClient
import com.example.viredapp.utilities.ApiClient
import com.example.viredapp.utilities.MyApplication
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.main_activity.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

//TODO:Complete Login Display if user not logged in
//TODO:Check for Errors and incomplete code completion
//TODO:Check for other incomplete work

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var token: String
        @SuppressLint("StaticFieldLeak")
        lateinit var prefConfig: PrefConfig
            private set
        val LOG_TAG = MainActivity::class.java.simpleName
        //Retrofit Initialization
        lateinit var apiInterface: UserClient

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefConfig = PrefConfig(this)
        //Check user login status
        //prefConfig.checkLogin()
        setContentView(R.layout.main_activity)

        //Button Initializations
        val login_btn = findViewById<Button>(R.id.login_auth_btn)
        val signUp_btn = findViewById<Button>(R.id.btn_signup)
        apiInterface = ApiClient.getApiClient().create(UserClient::class.java)

        //Set Click Listeners
        if (login_btn != null)
            login_btn.setOnClickListener {
                //Testing purpose code
                val username = et_email.text.toString().trim()
                val password = et_password.text.toString().trim()

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    toast("Enter All Details")
                } else {

                    login(username, password)
                    prefConfig.showToken()
                }

            }
        if (signUp_btn != null)
            signUp_btn.setOnClickListener {
                intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
    }

    //Login Function
    private fun login(user: String, password: String) {
        val init_login: Login = Login(user, password)
        val call: Call<User> = apiInterface.login(init_login)

        call.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Timber.i("Login Failed")
                Timber.d(t?.printStackTrace().toString())
                toast("Login Failed")
            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {

                val token: String = response?.body()!!.token

                prefConfig.saveToken(token)
                prefConfig.writeUsername(user)
                prefConfig.writeLoginStatus(true)
                showFeedActivtiy()
            }
        })
    }

    //Check for Empty Fields
    private fun checkDetailsFilled(): Boolean {
        val tag: String = "Enter"
        if (TextUtils.isEmpty(et_email_address.toString())) {
            toast(tag + "Email")
            return false
        }
        if (TextUtils.isEmpty(et_full_name.toString())) {
            toast(tag + "Name")
            return false
        }
        if (TextUtils.isEmpty(password.toString())) {
            toast(tag + "Password")
            return false
        }
        if (TextUtils.isEmpty(location.toString())) {
            toast(tag + "Location")
            return false
        }
        return true
    }

    //Simple Toast Display Message
    private fun toast(s: String) {
        Toast.makeText(MyApplication.getContext(), s, Toast.LENGTH_LONG).show()
    }

    private fun showFeedActivtiy(){
        toast("Login Successfull")

        val i:Intent = Intent(this,FeedActivity::class.java)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(i)
    }





}
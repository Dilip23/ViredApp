package com.example.viredapp.utilities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Toast;

import com.example.viredapp.R;
import com.example.viredapp.ui.MainActivity;
import com.example.viredapp.ui.SignUpActivity;

//Class for Shared Preferences to store user data for login and signup Authentication
public class PrefConfig {
    private SharedPreferences sharedPreferences;
    private Context context;
    private SharedPreferences.Editor editor;

    public PrefConfig(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    //Write Login Status
    public void writeLoginStatus(boolean status) {
        editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.pref_login_status), status);
        editor.commit();
    }

    //Read Login Status
    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status), false);
    }

    //Write Username
    public void writeUsername(String name) {
        editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.pref_user_name), name);
        editor.commit();
    }

    //Read Username
    public String readUsername() {
        return sharedPreferences.getString(context.getString(R.string.pref_user_name), "User");
    }

    //Check login status
    public void checkLogin() {
        //Check login Status
        if (!this.readLoginStatus()) {
            //Redirect to Login
            Intent i = new Intent(context, MainActivity.class);
            //Close all activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //Add new flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //Starting Login Activity(i.e..,Main Activity)
            context.startActivity(i);
        }


    }

    //Logout User
    public void logout() {
        //Clear all data from sharedPreferences
        editor.clear();
        editor.commit();
        //After logout,Redirect to Login Activity
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    //Save token
    public void saveToken(String token) {
        editor = sharedPreferences.edit();
        editor.putString("com.example.preference_token", token);
        editor.commit();

    }

    //Display token
    public String showToken() {
        String token_msg = sharedPreferences.getString("com.example.preference_token", "No Token");
        return token_msg;
    }

    //Save User ID
    public void writeUserId(String Id) {
        editor = sharedPreferences.edit();
        editor.putString("com.example.user_id", Id);
        editor.commit();
    }

    //Read User Id
    public String readUserId() {
        String Id = sharedPreferences.getString("com.example.user_id", null);
        return Id;
    }

    //Toast the message
    public void displayToast(String message) {
        Toast.makeText(context, "PrefConfig" + message, Toast.LENGTH_LONG).show();
    }
}
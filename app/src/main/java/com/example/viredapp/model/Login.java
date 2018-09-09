package com.example.viredapp.model;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;

/*
* Login class for checking user details while Authenticating
 * */
public class Login {

    @SerializedName("username")
    private String user;

    @SerializedName("password")
    private String password;

    public Login(String user,String password){
        this.user = user;
        this.password = password;
    }
}

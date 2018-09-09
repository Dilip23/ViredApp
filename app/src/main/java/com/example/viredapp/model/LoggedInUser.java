package com.example.viredapp.model;

import com.google.gson.annotations.SerializedName;

public class LoggedInUser {

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("location")
    private String location;

    @SerializedName("friends_count")
    private String friends_count;

    @SerializedName("profile_pic")
    private String profile_pic;


}

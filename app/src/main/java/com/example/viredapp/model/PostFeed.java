package com.example.viredapp.model;

import com.google.gson.annotations.SerializedName;

public class PostFeed {


    @SerializedName("m_url")
    private String m_Url;

    @SerializedName("location")
    private String location;

    public void setM_Url(String m_Url) {
        this.m_Url = m_Url;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

package com.example.viredapp.model;

import com.google.gson.annotations.SerializedName;

public class RequestName {

    @SerializedName("requested_id")
    private Integer requested_id;

    public RequestName(Integer name){
        this.requested_id = name;
    }

}

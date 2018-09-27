package com.example.viredapp.model;

import com.example.viredapp.db.feed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedResult {

    @SerializedName("count")
    @Expose
    private int count;


    @SerializedName("results")
    @Expose

    private List<feed> feedList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<feed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<feed> feedList) {
        this.feedList = feedList;
    }
}
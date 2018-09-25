package com.example.viredapp.model;


import java.util.List;

import com.example.viredapp.db.Request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestResult {




        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("next")
        @Expose
        private Object next;
        @SerializedName("previous")
        @Expose
        private Object previous;
        @SerializedName("results")
        @Expose
        private List<Request> results = null;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Object getNext() {
            return next;
        }

        public void setNext(Object next) {
            this.next = next;
        }

        public Object getPrevious() {
            return previous;
        }

        public void setPrevious(Object previous) {
            this.previous = previous;
        }

        public List<Request> getResults() {
            return results;
        }

        public void setResults(List<Request> results) {
            this.results = results;
        }

    }


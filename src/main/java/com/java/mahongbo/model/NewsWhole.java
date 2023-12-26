package com.java.mahongbo.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsWhole implements Serializable {
    @SerializedName("pageSize")
    private String pageSize;

    @SerializedName("total")
    private String total;

    @SerializedName("data")
    private List<NewsData> data;



    public List<NewsData> getData() {
        return data;
    }

   

}

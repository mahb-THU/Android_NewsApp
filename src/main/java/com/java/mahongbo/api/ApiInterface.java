package com.java.mahongbo.api;

import com.java.mahongbo.model.NewsWhole;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("queryNewsList")
    Call<NewsWhole> getCall(
            @Query("size") String size,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("words") String words,
            @Query("categories") String categories,
            @Query("page") String page
    );
}

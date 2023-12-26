package com.java.mahongbo.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static final String _URL = "https://api2.newsminer.net/svc/news/";
    private static Api apiclinet;
    private static Retrofit retrofit;

    private Api() {
        retrofit = new Retrofit.Builder()
                .baseUrl(_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Api getInstance() {
        if (apiclinet == null) {
            apiclinet = new Api();
        }
        return apiclinet;
    }
    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }
}

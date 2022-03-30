package com.example.fragments.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultConstants {

    public static final String API_KEY = "a73dd7eed2492e40d4db5d3e7557424f";
    public static final String SESSION_ID = "f5abacef69d05e33687ba8142af6c8df8e74f9ff";
    public static final String ACCOUNT_ID = "SergiiCastii";

    public static final String BASE_IMG_URL = "https://image.tmdb.org/t/p/w500/";

    public static final Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.themoviedb.org/3/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

}

package com.example.fragments.Config;

import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCall {
    @GET("account/SergiiCastii/lists?")
    Call<ListModel> getList(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @GET("movie/{movie_id}/account_states?")
    Call<FavFilmRequest> getState(@Path("movie_id") int movie_id, @Query("api_key") String api_key, @Query("session_id") String session_id);

    @GET("account/SergiiCastii/favorite/movies")
    Call<searchFilmModel> getFav(@Query("api_key") String api_key, @Query("session_id") String session_id);

    @GET("search/movie?")
    Call<searchFilmModel> getSearch(@Query("api_key") String api_key, @Query("query") String query);

    @POST("account/SergiiCastii/favorite?")
    Call<FavFilmResponse> postAddFav(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body FavFilmRequest film);

    @POST("list?")
    Call<ListModel> postAddList(@Query("api_key") String api_key, @Query("session_id") String session_id, @Body ListRequest list);
}

package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;
import static com.example.fragments.R.id.recyclerFav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Recyclers.FavoritesMovieRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoriteFragment extends Fragment {

    public String sectionTitle;
    public View view;
    RecyclerView recyclerView_fav;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public FavoriteFragment(String title) {
        this.sectionTitle = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        TextView txtSectionTitle = view.findViewById(R.id.sectionTitle);
        txtSectionTitle.setText(sectionTitle);
        recyclerView_fav = view.findViewById(recyclerFav);

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<searchFilmModel> call = apiCall.getFav(API_KEY, SESSION_ID);

        call.enqueue(new Callback<searchFilmModel>(){
            @Override
            public void onResponse(Call<searchFilmModel> call, Response<searchFilmModel> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    ArrayList<Film> arrayFav = new ArrayList<>();
                    arrayFav = response.body().getResults();
                    callRecycler(arrayFav);
                }
            }

            @Override
            public void onFailure(Call<searchFilmModel> call, Throwable t) {

            }
        });

        return view;
    }

    public void callRecycler(ArrayList<Film> arrayFav){
        FavoritesMovieRecyclerViewAdapter adapter = new FavoritesMovieRecyclerViewAdapter(arrayFav, getContext());
        recyclerView_fav.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView_fav.setAdapter(adapter);
    }
}
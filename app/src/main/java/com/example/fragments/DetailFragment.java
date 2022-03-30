package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.BASE_IMG_URL;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;
import static com.example.fragments.R.id.recyclerFav;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Config.GlideApp;
import com.example.fragments.Model.Film.FavFilmRequest;
import com.example.fragments.Model.Film.FavFilmResponse;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.List.List;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;
import com.example.fragments.Recyclers.FavoritesMovieRecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailFragment extends Fragment {

    public View view;
    private Film film;
    private ImageButton btnFav;
    private RecyclerView recyclerView_fav;
    private boolean favStatus;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getArguments();
        film = (Film) bundle.getSerializable("Film");

        TextView txtDetailTitle = view.findViewById(R.id.txtDetailTitle);
        TextView txtDetailDesc = view.findViewById(R.id.txtDetailDesc);
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        btnFav = view.findViewById(R.id.btnFav);
        ImageButton btnAddtoList = view.findViewById(R.id.btnAddtoList);
        recyclerView_fav = view.findViewById(recyclerFav);



        txtDetailTitle.setText(film.getOriginal_title());
        txtDetailDesc.setText(film.getOverview());

        GlideApp.with(getContext())
                .load(BASE_IMG_URL + film.getPoster_path())
                .centerCrop()
                .into(imgDetail);

        StatusFav();
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favStatus == true) {
                    AddFav(false);
                    favStatus = false;
                    btnFav.setImageResource(R.drawable.ic_fav_off);
                } else {
                    AddFav(true);
                    favStatus = true;
                    btnFav.setImageResource(R.drawable.ic_fav_on);
                }
            }
        });

        btnAddtoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        return view;

    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_movie_to_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();


        ArrayList<List> arrayList = new ArrayList<List>();
        arrayList.add(new List("Comedia", 8));
        arrayList.add(new List("Ciència", 8));
        arrayList.add(new List("Terror", 8));
        arrayList.add(new List("Comedia", 8));
        arrayList.add(new List("Ciència", 8));
        arrayList.add(new List("Terror", 8));
        arrayList.add(new List("Comedia", 8));
        arrayList.add(new List("Ciència", 8));
        arrayList.add(new List("Terror", 8));


        RecyclerView recyclerView = alertCustomdialog.findViewById(R.id.recyclerList);
        AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void AddFav(boolean status){
        ApiCall apiCall = retrofit.create(ApiCall.class);
        FavFilmRequest favRequest = new FavFilmRequest("movie", film.getId(), status);
        Call<FavFilmResponse> callAddFav = apiCall.postAddFav(API_KEY, SESSION_ID, favRequest);

        callAddFav.enqueue(new Callback<FavFilmResponse>(){
            @Override
            public void onResponse(Call<FavFilmResponse> call, Response<FavFilmResponse> response) {
                if(response.code()!=201){
                    Log.i("testApi", "checkConnection");
                    return;
                }else{
                    Toast.makeText(getContext(), "Film added!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavFilmResponse> call, Throwable t) {

            }
        });
    }

    public void StatusFav(){
        ApiCall apiCall = retrofit.create(ApiCall.class);

        Call<FavFilmRequest> callState = apiCall.getState(film.getId(), API_KEY, SESSION_ID);

        callState.enqueue(new Callback<FavFilmRequest>() {
            @Override
            public void onResponse(Call<FavFilmRequest> call, Response<FavFilmRequest> response) {
                try {
                    if (response.code()!=200) {
                        Log.i("testApi", "sin conexión api");
                    } else {
                        FavFilmRequest favFilm = response.body();
                        Log.i("testApi", "im in");

                        if (favFilm.isFavorite()) {
                            Log.i("testApi", "im in fav_on");
                            favStatus = true;
                            btnFav.setImageResource(R.drawable.ic_fav_on);
                        } else {
                            Log.i("testApi", "im in fav_off");
                            favStatus = false;
                            btnFav.setImageResource(R.drawable.ic_fav_off);
                        }

                    }
                } catch(Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FavFilmRequest> call, Throwable t) {
                Toast.makeText(getActivity(), "Error de conexión", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
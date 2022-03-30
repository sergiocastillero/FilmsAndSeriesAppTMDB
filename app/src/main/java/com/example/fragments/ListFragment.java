package com.example.fragments;

import static com.example.fragments.Config.DefaultConstants.API_KEY;
import static com.example.fragments.Config.DefaultConstants.SESSION_ID;
import static com.example.fragments.Config.DefaultConstants.retrofit;
import static com.example.fragments.R.id.recyclerList;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fragments.Config.ApiCall;
import com.example.fragments.Model.Film.Film;
import com.example.fragments.Model.Film.searchFilmModel;
import com.example.fragments.Model.List.List;
import com.example.fragments.Model.List.ListModel;
import com.example.fragments.Model.List.ListRequest;
import com.example.fragments.Recyclers.AddMovieListsRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListFragment extends Fragment {
    private RecyclerView recyclerLists;
    private ApiCall apiCall;

    public ListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        FloatingActionButton btnAdd = view.findViewById(R.id.btnAddList);
        recyclerLists = view.findViewById(R.id.recyclerLists);




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        ApiCall apiCall = retrofit.create(ApiCall.class);

        Call<ListModel> call = apiCall.getList(API_KEY, SESSION_ID);

        call.enqueue(new Callback<ListModel>(){
            @Override
            public void onResponse(Call<ListModel> call, Response<ListModel> response) {
                if(response.code()!=200){
                    Log.i("testApi", "checkConnection");
                    return;
                }else {
                    ArrayList<List> arrayList = new ArrayList<>();
                    arrayList = response.body().getResult();
                    callRecycler(arrayList);
                }
            }

            @Override
            public void onFailure(Call<ListModel> call, Throwable t) {

            }
        });
        return view;
    }

    public void showDialog(){
        View alertCustomdialog = getLayoutInflater().inflate( R.layout.form_add_list, null);

        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        Button btnSaveList = alertCustomdialog.findViewById(R.id.btnSaveList);
        EditText name = alertCustomdialog.findViewById(R.id.txtList);
        EditText description = alertCustomdialog.findViewById(R.id.txtDescription);

        btnSaveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListRequest list = new ListRequest(name.getText().toString(), description.getText().toString(), "spanish");

                apiCall = retrofit.create(ApiCall.class);

                Call<ListModel> callList = apiCall.postAddList(API_KEY, SESSION_ID, list);

                callList.enqueue(new Callback<ListModel>(){
                    @Override
                    public void onResponse(Call<ListModel> call, Response<ListModel> response) {
                        if(response.code()!=201){
                            Log.i("testApi", "checkConnection");
                            return;
                        }else{
                            Toast.makeText(getContext(), "List added!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ListModel> call, Throwable t) {

                    }
                });
                dialog.dismiss();
            }
        });
    }

    public void callRecycler(ArrayList<List> arrayList){
        AddMovieListsRecyclerViewAdapter adapter = new AddMovieListsRecyclerViewAdapter(arrayList, getContext());
        recyclerLists.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerLists.setAdapter(adapter);
    }
}
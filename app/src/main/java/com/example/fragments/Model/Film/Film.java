package com.example.fragments.Model.Film;

import java.io.Serializable;

public class Film implements Serializable {
    private int id;
    private String original_title;
    private double vote_average;
    private String poster_path;
    private String overview;
    private String release_date;
    private FavFilmRequest state;


    public String getOriginal_title() {
        return original_title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public FavFilmRequest getState() {
        return state;
    }
}
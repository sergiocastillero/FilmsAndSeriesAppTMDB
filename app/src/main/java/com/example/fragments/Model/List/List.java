package com.example.fragments.Model.List;

import com.example.fragments.Model.Film.FavFilmRequest;

import java.util.ArrayList;

public class List {
    public String title;
    public int count;
    private ListRequest addList;

    public List(String title, int count) {
        this.title = title;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ListRequest getAddList() {
        return addList;
    }

    public void setAddList(ListRequest addList) {
        this.addList = addList;
    }
}

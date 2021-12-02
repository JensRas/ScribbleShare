package com.example.scribbleshare.searchpage;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.scribbleshare.homepage.HomePageView;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.IVolleyListener;

import org.json.JSONArray;

public class SearchPresenter implements IVolleyListener<JSONArray> {

    private EndpointCaller<JSONArray> model;
    private SearchPageView view;
    private Context context;

    public SearchPresenter(SearchPageView view, Context c) {
        this.view = view;
        this.context = c;
        this.model = new EndpointCaller<>(c, this);
    }

    public void performSearch(String search) {
        model.createSearchRequest(search);
    }

    @Override
    public void onSuccess(JSONArray jsonArray) {
        view.setSearchResults(jsonArray);
    }

    @Override
    public void onError(VolleyError e) {
        Log.e("ERROR", "Search presenter error: " + e);
    }
}

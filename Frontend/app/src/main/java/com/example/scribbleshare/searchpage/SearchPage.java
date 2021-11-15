package com.example.scribbleshare.searchpage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;

import org.json.JSONArray;

/**
 * TODO
 */
public class SearchPage extends AppCompatActivity implements SearchPageView {
    /**
     * TODO implement
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();
    }

    @Override
    public void setSearchResults(JSONArray a) {

    }
}

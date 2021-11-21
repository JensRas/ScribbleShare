package com.example.scribbleshare.searchpage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.homepage.PostModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity implements SearchPageView {

    private RecyclerView searchRV;
    private ArrayList<SearchModel> searchAL;

    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPresenter = new SearchPresenter(this, getApplicationContext());

        searchAL = new ArrayList<>(); //start with empty search results

        SearchAdapter searchAdapter = new SearchAdapter(this, searchAL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        setContentView(R.layout.activity_search);
        searchRV = findViewById(R.id.users_recycler_view);
        searchRV.setLayoutManager(linearLayoutManager);
        searchRV.setAdapter(searchAdapter);

        searchPresenter.performSearch("%20"); //perform all search on page load

        EditText usernameSearch = findViewById(R.id.username_search);
        usernameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = usernameSearch.getText().toString();
                if(text.isEmpty()){
                    searchPresenter.performSearch("%20"); //space encoding
                }else {
                    searchPresenter.performSearch(text.trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void setSearchResults(JSONArray array) {
        searchAL = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject obj = (JSONObject)array.get(i);
                String username = obj.getString("username");
                int followerCount = 0; //TODO add once implemented in backend
                SearchModel s = new SearchModel(username, followerCount);
                searchAL.add(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SearchAdapter searchAdapter = new SearchAdapter(this, searchAL);
        searchRV.setAdapter(searchAdapter);
    }

    @Override
    public void refreshSearch(String search) {

    }
}

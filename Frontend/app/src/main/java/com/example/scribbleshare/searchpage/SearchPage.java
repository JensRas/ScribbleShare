package com.example.scribbleshare.searchpage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.PostModel;
import com.example.scribbleshare.profilepage.ProfilePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import com.example.scribbleshare.activitypage.ActivityPage;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.profilepage.ProfilePage;

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

//        searchPresenter.performSearch("%20"); //perform all search on page load

        EditText usernameSearch = findViewById(R.id.username_search);
        usernameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = usernameSearch.getText().toString();
                if(text.isEmpty()){
//                    searchPresenter.performSearch("%20"); //space encoding
                }else {
                    searchPresenter.performSearch(text.trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Icon buttons
        ImageButton home_button = (ImageButton) findViewById(R.id.btn_home);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), HomePage.class));
            }
        });

        ImageButton create_new_button = (ImageButton) findViewById(R.id.btn_create_new);
        create_new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DrawingPage.class);
                intent.putExtra("drawContext", "newPost");
                startActivity(intent);
            }
        });

        ImageButton likes_button = (ImageButton) findViewById(R.id.btn_activity);
        likes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityPage.class));
            }
        });

        ImageButton profile_button = (ImageButton) findViewById(R.id.btn_profile);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfilePage.class));
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
                int followerCount = 0;
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
        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();

        // Icon buttons
        ImageButton home_button = (ImageButton) findViewById(R.id.btn_home);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), HomePage.class));
            }
        });

        ImageButton search_button = (ImageButton) findViewById(R.id.btn_search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //already on search page
                //startActivity(new Intent(view.getContext(), SearchPage.class));
            }
        });

        ImageButton create_new_button = (ImageButton) findViewById(R.id.btn_create_new);
        create_new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DrawingPage.class);
                intent.putExtra("drawContext", "newPost");
                startActivity(intent);
            }
        });

        ImageButton activity_button = (ImageButton) findViewById(R.id.btn_activity);
        activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityPage.class));
            }
        });

        ImageButton profile_button = (ImageButton) findViewById(R.id.btn_profile);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfilePage.class));
            }
        });
    }
}

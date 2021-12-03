package com.example.scribbleshare.profilepage;


import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.homepage.PostModel;
import com.example.scribbleshare.homepage.PostsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * TODO implement
 */
public class ProfilePage extends AppCompatActivity implements ProfilePageView {

    private RecyclerView postsRV;
    private ArrayList<PostModel> postsAL;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePagePresenter presenter = new ProfilePagePresenter(this, getApplicationContext());
        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();

        //presenter.getFollowers(username); //when the request is done it calls "setHomePagePosts below
        presenter.getUserPosts(username);

    }

    public void setUserPosts(JSONArray array){
        postsAL = new ArrayList<>();

        //iterate over the array and populate postsAL with user posts
        for(int i = 0; i < array.length(); i++){
            try{
                JSONObject obj = (JSONObject)array.get(i);
                String id = obj.getString("id");
                String profileName = "test";//obj.getString("username");
                int likeCount = obj.getInt("likeCount");
                int commentCount = obj.getInt("commentCount");
                PostModel m = new PostModel(id, profileName, likeCount, commentCount);
                postsAL.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ProfileAdapter profileAdapter = new ProfileAdapter(this, postsAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        postsRV = findViewById(R.id.profile_gallery_layout);
        postsRV.setLayoutManager(gridLayoutManager);
        postsRV.setAdapter(profileAdapter);

        //setContentView(R.layout.activity_profile);
    }




}
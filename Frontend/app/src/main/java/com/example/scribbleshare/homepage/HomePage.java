package com.example.scribbleshare.homepage;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements HomePageView{

    private RecyclerView postsRV;
    private ArrayList<PostModel> postsAL;

    private GetPostsPresenter postsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postsPresenter = new GetPostsPresenter(this, getApplicationContext());
        postsPresenter.populateHomeScreenPosts(); //when the request is done it calls "setHomePagePosts below
    }

    @Override
    public void setHomePagePosts(JSONArray array) {
        Log.e("setHomePagePosts", "calling method");
        postsAL = new ArrayList<>();

        //iterate over the array and populate postsAL with new posts
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject obj = (JSONObject)array.get(i);
                String id = obj.getString("id");
                String profileName = obj.getString("username");
                int likeCount = obj.getInt("likeCount");
                int commentCount = obj.getInt("commentCount");
                PostModel m = new PostModel(id, profileName, likeCount, commentCount);
                postsAL.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        PostsAdapter adapterPost = new PostsAdapter(this, postsAL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        setContentView(R.layout.activity_home);
        postsRV = findViewById(R.id.post_recycler_view);
        postsRV.setLayoutManager(linearLayoutManager);
        postsRV.setAdapter(adapterPost);
    }
}

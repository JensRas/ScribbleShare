package com.example.scribbleshare.profilepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.activitypage.ActivityPage;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.GetPostIsLikedPresenter;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.postpage.PostPage;
import com.example.scribbleshare.homepage.PostModel;
import com.example.scribbleshare.searchpage.SearchPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the RecyclerView for the homepage
 */
public class OtherProfilePage extends AppCompatActivity implements ProfilePageView {

    private RecyclerView postsRV;
    private ArrayList<PostModel> postsAL;
    private GetFollowingPresenter getFollowingPresenter;
    private boolean isUserFollowing;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merged_profile_views);
        getFollowingPresenter = new GetFollowingPresenter(this, getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        ProfilePagePresenter presenter = new ProfilePagePresenter(this, getApplicationContext());

        TextView profileName = (TextView)findViewById(R.id.profile_profile_name);
        username = bundle.getString("username");
        profileName.setText(username);//bundle.getString("username"));

        TextView postNum = (TextView)findViewById(R.id.post_count);


        getFollowingPresenter.setIsFollowing(username, MySingleton.getInstance(this).getApplicationUser().getUsername());

        postNum.setText(isUserFollowing + "");


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
                startActivity(new Intent(view.getContext(), SearchPage.class));
            }
        });

        Button follow_button = (Button) findViewById(R.id.follow_button);
        Button following_button = (Button) findViewById(R.id.following_button);

        if(isUserFollowing){
            following_button.setVisibility(View.VISIBLE);
            follow_button.setVisibility(View.GONE);
            following_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    following_button.setVisibility(View.GONE);
                    follow_button.setVisibility(View.VISIBLE);
                }
            });}
        else{
            following_button.setVisibility(View.GONE);
            follow_button.setVisibility(View.VISIBLE);
            following_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    following_button.setVisibility(View.VISIBLE);
                    follow_button.setVisibility(View.GONE);
                }
            });}


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
                // Already on profile page
                //startActivity(new Intent(view.getContext(), ProfilePage.class));
            }
        });

        presenter.getUserPosts(bundle.getString("username"));
    }

    public void setUserPosts(JSONArray array){
        postsAL = new ArrayList<>();

        //iterate over the array and populate postsAL with user posts
        for(int i = 0; i < array.length(); i++){
            try{
                JSONObject obj = (JSONObject)array.get(i);
                int id = obj.getInt("id");
                String profileName = ((JSONObject)obj.get("user")).getString("username");
                int likeCount = obj.getInt("likeCount");
                int commentCount = obj.getInt("commentCount");
                PostModel m = new PostModel(id, profileName, likeCount, commentCount, false); //TODO change default isLiked?
                postsAL.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ProfileAdapter profileAdapter = new ProfileAdapter(this, postsAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        //setContentView(R.layout.activity_profile);
        postsRV = findViewById(R.id.profile_gallery_layout);
        postsRV.setLayoutManager(gridLayoutManager);
        postsRV.setAdapter(profileAdapter);
    }

    public void setUserFollowing(JSONObject object){
       try {
            isUserFollowing = object.getBoolean("following");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }



}

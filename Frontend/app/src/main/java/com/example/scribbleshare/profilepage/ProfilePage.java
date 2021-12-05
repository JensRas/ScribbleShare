package com.example.scribbleshare.profilepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
<<<<<<< HEAD
import com.example.scribbleshare.homepage.PostModel;
import com.example.scribbleshare.homepage.PostsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

=======
import com.example.scribbleshare.SplashScreen;
>>>>>>> 8de0872e0df68277845fd7ec6b7b2bd2e5b568aa
import com.example.scribbleshare.activitypage.ActivityPage;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.searchpage.SearchPage;


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
        //presenter.getUserPosts(username);

        TextView profileName = (TextView)findViewById(R.id.profile_profile_name);
        profileName.setText(username);

        TextView postNum = (TextView)findViewById(R.id.post_count);
        postNum.setText("posts");

        //presenter.getFollowers(username); //when the request is done it calls "setHomePagePosts below

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

        ImageButton logout_button = (ImageButton) findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SplashScreen.class));
            }
        });

        presenter.getUserPosts(username);
    }

    public void setUserPosts(JSONArray array){
        postsAL = new ArrayList<>();

        //iterate over the array and populate postsAL with user posts
        for(int i = 0; i < array.length(); i++){
            try{
                JSONObject obj = (JSONObject)array.get(i);
                String id = obj.getString("id");
                String profileName = ((JSONObject)obj.get("user")).getString("username");
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

        //setContentView(R.layout.activity_profile);
        postsRV = findViewById(R.id.profile_gallery_layout);
        postsRV.setLayoutManager(gridLayoutManager);
        postsRV.setAdapter(profileAdapter);
    }




}
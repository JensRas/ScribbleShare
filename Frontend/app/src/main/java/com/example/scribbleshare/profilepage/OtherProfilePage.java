package com.example.scribbleshare.profilepage;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.User;
import com.example.scribbleshare.activitypage.ActivityPage;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.homepage.PostModel;
import com.example.scribbleshare.searchpage.SearchPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Handles the RecyclerView for the homepage
 */
public class OtherProfilePage extends AppCompatActivity implements ProfilePageView {

    private RecyclerView postsRV;
    private ArrayList<PostModel> postsAL;
    private GetFollowingPresenter getFollowingPresenter;
    private AddFollowerPresenter addFollowerPresenter;
    private UnfollowUserPresenter unfollowUserPresenter;
    private GetUserStatsPresenter getUserStatsPresenter;
    private boolean isUserFollowing;
    private String username;
    private String singletonUsername;
    private BanUserPresenter banUserPresenter;
    private UnbanUserPresenter unbanUserPresenter;
    private GetUserBanStatusPresenter getUserBanStatusPresenter;
    private User user;

    ImageButton ban_hammer;

    int isBannedColor = R.color.green;
    int isUnBannedColor = R.color.red_orange;

    boolean isBanned = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");


        setContentView(R.layout.activity_merged_profile_views);


        getFollowingPresenter = new GetFollowingPresenter(this, getApplicationContext());
        addFollowerPresenter = new AddFollowerPresenter(this, getApplicationContext());
        unfollowUserPresenter = new UnfollowUserPresenter(this, getApplicationContext());
        banUserPresenter = new BanUserPresenter(this, getApplicationContext());
        unbanUserPresenter = new UnbanUserPresenter(this, getApplicationContext());
        getUserBanStatusPresenter = new GetUserBanStatusPresenter(this, getApplicationContext());
        getUserStatsPresenter = new GetUserStatsPresenter(this, getApplicationContext());

        ban_hammer = (ImageButton) findViewById(R.id.banHammer);

        ProfilePagePresenter presenter = new ProfilePagePresenter(this, getApplicationContext());

        TextView profileName = (TextView)findViewById(R.id.profile_profile_name);

        singletonUsername = MySingleton.getInstance(this).getApplicationUser().getUsername();
        profileName.setText(username);//bundle.getString("username"));

        TextView postNum = (TextView)findViewById(R.id.post_count);

        getFollowingPresenter.setIsFollowing(singletonUsername, username);
        getUserStatsPresenter.getUserStats(username);

        getUserBanStatusPresenter.getUserBanStatus(username);

        if(MySingleton.getInstance(this).getApplicationUser().getPermissionLevel().equals("moderator")){
            ban_hammer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isBanned){
                        unbanUserPresenter.unbanUser(username);
                    }else{
                        banUserPresenter.banUser(username);
                    }
                }
            });
            ban_hammer.setVisibility(View.VISIBLE);
        }else{
            ban_hammer.setVisibility(View.GONE);
        }

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
                startActivity(new Intent(view.getContext(), ProfilePage.class));
            }
        });

        presenter.getUserPosts(bundle.getString("username"));
    }

    public void setUserPosts(JSONArray array){
        postsAL = new ArrayList<>();

        TextView postCount = (TextView)findViewById(R.id.post_count);
        postCount.setText(Integer.toString(array.length()));

        //iterate over the array and populate postsAL with user posts
        for(int i = 0; i < array.length(); i++){
            try{
                JSONObject obj = (JSONObject)array.get(i);
                int id = obj.getInt("id");
                String profileName = ((JSONObject)obj.get("user")).getString("username");
                int likeCount = obj.getInt("likeCount");
//                int commentCount = obj.getInt("commentCount");
                PostModel m = new PostModel(id, profileName, likeCount, 0, false); //TODO change default isLiked?
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

           Button follow_button = (Button) findViewById(R.id.follow_button);
           Button following_button = (Button) findViewById(R.id.following_button);

           if (isUserFollowing) {
               following_button.setVisibility(View.VISIBLE);
               follow_button.setVisibility(View.GONE);
           } else {
               following_button.setVisibility(View.GONE);
               follow_button.setVisibility(View.VISIBLE);
           }
           follow_button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   addFollowerPresenter.addFollower(singletonUsername, username);
                   following_button.setVisibility(View.VISIBLE);
                   follow_button.setVisibility(View.GONE);
               }
           });

           following_button.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   unfollowUserPresenter.unfollowUser(singletonUsername, username);
                   following_button.setVisibility(View.GONE);
                   follow_button.setVisibility(View.VISIBLE);
               }
           });
       }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserBanned(JSONObject object) {
        getUserBanStatusPresenter.getUserBanStatus(username);
        ban_hammer.setColorFilter(ContextCompat.getColor(this, isBannedColor));
    }

    @Override
    public void setUserUnbanned(JSONObject object) {
        getUserBanStatusPresenter.getUserBanStatus(username);
        ban_hammer.setColorFilter(ContextCompat.getColor(this, isUnBannedColor));
    }

    @Override
    public void setUserBannedStatus(JSONObject object) {
        try {
            boolean responseIsBanned = object.getBoolean("isBanned");
            isBanned = responseIsBanned;
            if(responseIsBanned){
                ban_hammer.setColorFilter(ContextCompat.getColor(this, isBannedColor));
            }else{
                ban_hammer.setColorFilter(ContextCompat.getColor(this, isUnBannedColor));
            }
        } catch (JSONException e) {
            Log.e("ERROR", "error getting is_banned from user json object!");
            e.printStackTrace();
        }

    }

    public void setUserStats(JSONObject object){
        try {
            int followers = object.getInt("followers");
            int following = object.getInt("following");

            TextView numFollowers = (TextView)findViewById(R.id.followers_count);
            numFollowers.setText(followers + "");
            TextView numFollowing = (TextView)findViewById(R.id.following_count);
            numFollowing.setText(following + "");

        }catch(JSONException e) {
            e.printStackTrace();
        }
    }

}

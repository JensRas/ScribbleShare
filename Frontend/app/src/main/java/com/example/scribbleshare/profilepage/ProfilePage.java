package com.example.scribbleshare.profilepage;


import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;


public class ProfilePage extends AppCompatActivity implements ProfilePageView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePagePresenter presenter = new ProfilePagePresenter(this, getApplicationContext());
        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();
        int followers = MySingleton.getInstance(this).getApplicationUser().getFollowers();
        int following = MySingleton.getInstance(this).getApplicationUser().getFollowers();
        presenter.getFollowers(username); //when the request is done it calls "setHomePagePosts below
        TextView profileName, textFollowers, textFollowing;


        profileName = (TextView) findViewById(R.id.profileName);
        textFollowers = (TextView) findViewById(R.id.followers);
        textFollowing = (TextView) findViewById(R.id.following);

        presenter.getFollowersNum(username);


        profileName.setText(username);
        textFollowers.setText("Followers: " + followers);
        textFollowing.setText("Following: " + following);
    }


}
package com.example.scribbleshare.profilepage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;

/**
 * TODO implement
 */
public class ProfilePage extends AppCompatActivity implements ProfilePageView {
    /**
     * TODO implement
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfilePagePresenter presenter = new ProfilePagePresenter(this, getApplicationContext());
        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();
        presenter.getFollowers(username); //when the request is done it calls "setHomePagePosts below
    }
}

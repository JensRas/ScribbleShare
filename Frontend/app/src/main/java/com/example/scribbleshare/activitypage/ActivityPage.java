package com.example.scribbleshare.activitypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scribbleshare.R;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.profilepage.ProfilePage;
import com.example.scribbleshare.searchpage.SearchPage;

/**
 * TODO
 */
public class ActivityPage extends AppCompatActivity implements ActivityPageView {
    /**
     * TODO implement
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

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
                //already on activity page
                //startActivity(new Intent(view.getContext(), ActivityPage.class));
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

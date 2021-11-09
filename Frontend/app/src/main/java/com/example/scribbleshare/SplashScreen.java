package com.example.scribbleshare;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scribbleshare.createaccountpage.CreateAccountPage;
import com.example.scribbleshare.signinpage.SignInPage;

/**
 * The entry point of the application for new users. It allows users to log in or create a new account.
 */
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button create_account=(Button) findViewById(R.id.button);
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CreateAccountPage.class));
            }
        });

        TextView sign_in = (TextView) findViewById(R.id.sign_in_home);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SignInPage.class));
            }
        });
    }
}
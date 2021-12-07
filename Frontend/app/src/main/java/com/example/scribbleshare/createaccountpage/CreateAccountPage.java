package com.example.scribbleshare.createaccountpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.SplashScreen;
import com.example.scribbleshare.R;
import com.example.scribbleshare.User;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.signinpage.SignInPage;


/**
 * Handles the UI of creating an account. Also handles button clicks
 */
public class CreateAccountPage extends AppCompatActivity implements CreateAccountView {
    private CreateAccountPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        presenter = new CreateAccountPresenter(this, getApplicationContext());

        ImageButton back_button = (ImageButton) findViewById(R.id.back_button_create_account);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SplashScreen.class));
            }
        });

        Button create_account_button = (Button) findViewById(R.id.create_account_button);
        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = ((EditText) findViewById(R.id.ca_username)).getText().toString();
                String passwordText = ((EditText)findViewById(R.id.ca_password_text)).getText().toString();
                if(usernameText.equals("") || passwordText.equals("")){
                    return;
                }
                presenter.createAccountRequest(usernameText, passwordText);
            }
        });
        Context c = this;
        TextView sign_in_text = (TextView) findViewById(R.id.browse_posts_link_text);
        sign_in_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySingleton.getInstance(c).setApplicationUser(new User("GUEST", null, true, false, 0));
                Log.d("GUEST:", "Guest login");
                makeToast("Logging in as a Guest");
                startActivity(new Intent(view.getContext(), HomePage.class));
            }
        });
    }

    @Override
    public void makeToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    @Override
    public void switchView(Class c) {
        startActivity(new Intent(this, c));
    }

}
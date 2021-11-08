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

import com.example.scribbleshare.MainActivity;
import com.example.scribbleshare.R;
import com.example.scribbleshare.signinpage.SignIn;

/**
 *
 */
public class CreateAccount extends AppCompatActivity implements CreateAccountView {
    private CreateAccountPresenter presenter;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        presenter = new CreateAccountPresenter(this, getApplicationContext());

        TextView sign_in_text = (TextView) findViewById(R.id.sign_in_link_text);
        sign_in_text.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SignIn.class));
            }
        });

        ImageButton back_button = (ImageButton) findViewById(R.id.back_button_create_account);
        back_button.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });

        Button create_account_button = (Button) findViewById(R.id.create_account_button);
        create_account_button.setOnClickListener(new View.OnClickListener() {
            /**
             *
             * @param view
             */
            @Override
            public void onClick(View view) {
                String usernameText = ((EditText) findViewById(R.id.ca_username)).getText().toString();
                String passwordText = ((EditText)findViewById(R.id.ca_password_text)).getText().toString();
                if(usernameText.equals("") || passwordText.equals("")){
                    //TODO add better login checking and error messages to the user (eg. "this username already exists")
                    return;
                }
                presenter.createAccountRequest(usernameText, passwordText);
                Log.d("userCreated", "Attempting to create user with: "+ usernameText  + " and password: " + passwordText);
            }
        });
    }

    /**
     *
     * @param message
     */
    @Override
    public void makeToast(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    /**
     * 
     * @param c
     */
    @Override
    public void switchView(Class c) {
        startActivity(new Intent(this, c));
    }

}
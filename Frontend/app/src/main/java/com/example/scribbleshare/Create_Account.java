package com.example.scribbleshare;

import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class Create_Account extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
//    private String url = "http://www.mocky.io/v2/597c41390f0000d002f4dbd1";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        TextView sign_in_text = (TextView) findViewById(R.id.sign_in_link_text);
        sign_in_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), sign_in.class));
            }
        });

        ImageButton back_button = (ImageButton) findViewById(R.id.back_button_create_account);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });

        Button create_account_button = (Button) findViewById(R.id.create_account_button);
        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = ((EditText) findViewById(R.id.ca_username)).getText().toString();
                String passwordText = ((EditText)findViewById(R.id.ca_password_text)).getText().toString();
                if(usernameText.equals("") || passwordText.equals("")){
                    //TODO add better login checking and error messages to the user (eg. "this username already exists")
                    return;
                }
                userCreateAccountRequest(view, usernameText, passwordText);
                Log.d("userCreated", "Attempting to create user with: "+ usernameText  + " and password: " + passwordText);
            }
        });
    }

    private void userCreateAccountRequest(View view, String username, String password) {
//        String url = "http://10.0.2.2:8080/users/new?username=" + username + "&password=" + password;
        String url = "http://coms-309-010.cs.iastate.edu:8080/users/new?username=" + username + "&password=" + password;

        StringRequest request = new StringRequest(
                Request.Method.PUT,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        if(response.equals("new user created")){
                            startActivity(new Intent(view.getContext(), MainActivity.class));
                            Context context = getApplicationContext();
                            CharSequence text = "Account Created";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        } else if(response.equals("username already exists")) {
                            Toast.makeText(getApplicationContext(), "Username Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.toString());
                    }
                }
        );

        MySingleton.getInstance(this).addToRequestQueue(request);
    }
}
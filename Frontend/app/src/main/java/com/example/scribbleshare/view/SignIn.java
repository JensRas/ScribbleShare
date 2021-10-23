package com.example.scribbleshare.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.presenter.CreateAccountPresenter;
import com.example.scribbleshare.presenter.SignInPresenter;
import com.example.scribbleshare.test_homescreen;
import com.example.scribbleshare.view.MainActivity;

public class SignIn extends AppCompatActivity implements SignInView{

    private SignInPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        presenter = new SignInPresenter(this, getApplicationContext());

        ImageButton back_button = (ImageButton) findViewById(R.id.back_button_sign_in);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), MainActivity.class));
            }
        });

        Button sign_in_button = (Button) findViewById(R.id.create_account_button);
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameText = ((EditText) findViewById(R.id.si_username)).getText().toString();
                String passwordText = ((EditText)findViewById(R.id.si_password_text)).getText().toString();
                if(usernameText.equals("") || passwordText.equals("")){
                    //dont bother with empty credentials
                    return;
                }
//                userLoginRequest(view, usernameText, passwordText);
                presenter.signInRequest(usernameText, passwordText);
                Log.d("userCreated", "Attempting to login user with: " + usernameText + " and password: " + passwordText);
            }
        });
    }

    private void userLoginRequest(View view, String username, String password) {
//        String url = "http://10.0.2.2:8080/users/login?username=" + username + "&password=" + password;
        String usernameCheck = "http://coms-309-010.cs.iastate.edu:8080/users/new";
        String url = "http://coms-309-010.cs.iastate.edu:8080/users/login?username=" + username + "&password=" + password;

        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response.toString());
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;
                        if(response.equals("true")){
                            Log.d("signInSuccess", "Sign in successful");
                            startActivity(new Intent(view.getContext(), test_homescreen.class));
                            CharSequence text = "Signed In";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }else{
                            CharSequence text = "Username/Password Invalid";
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        MySingleton.getInstance(this).addToRequestQueue(request);
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
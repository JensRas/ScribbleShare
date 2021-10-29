package com.example.scribbleshare.searchpage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.createaccountpage.CreateAccountView;

public class SearchPage extends AppCompatActivity implements SearchPageView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();

    }
}

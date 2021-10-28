package com.example.scribbleshare.homepage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.R;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    private RecyclerView postsRV;
    private ArrayList<PostModel> postsAL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        postsRV = findViewById(R.id.post_recycler_view);

        postsAL = new ArrayList<>();
        postsAL.add(new PostModel("1", "post1", 0, 0));
        postsAL.add(new PostModel("1", "post2", 69, 69));
        postsAL.add(new PostModel("1", "post3", 420, 420));

        AdapterPosts adapterPost = new AdapterPosts(this, postsAL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postsRV.setLayoutManager(linearLayoutManager);
        postsRV.setAdapter(adapterPost);
    }
}

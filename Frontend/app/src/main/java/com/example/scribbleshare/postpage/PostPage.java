package com.example.scribbleshare.postpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.R;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.profilepage.ProfilePage;
import com.example.scribbleshare.searchpage.SearchPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostPage extends AppCompatActivity implements PostView{

    private FramePresenter framePresenter;
    private CommentPresenter commentPresenter;

    private RecyclerView framesRV;
    private ArrayList<FrameModel> framesAL;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        framePresenter = new FramePresenter(this, getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String postId = bundle.getString("postId");
            framePresenter.getFrames(postId);
        }else{
            //TODO show an error?
            Log.e("ERROR", "Bundle EMPTY when starting post page");
        }
        commentPresenter = new CommentPresenter();

    }

    @Override
    public void setFrames(JSONArray array) {
        Log.d("debug", "set frames in PostPage called");
        framesAL = new ArrayList<>();

        //iterate over the array and populate postsAL with new posts
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject obj = (JSONObject)array.get(i);
                int id = obj.getInt("id");
                Log.d("debug", "got id: " + id);
                JSONArray comments = obj.getJSONArray("comments");

                //get comment data inside of frame
                ArrayList<CommentModel> commentModels = new ArrayList<>();
                for(int j = 0; j < comments.length(); j++){
                    JSONObject commentObj = (JSONObject)comments.get(j);
                    int commentId = commentObj.getInt("id");
                    String commentProfileName = commentObj.getString("username");
                    int likeCount = commentObj.getInt("likeCount");
                    commentModels.add(new CommentModel(commentId, commentProfileName, likeCount));
                }

                FrameModel m = new FrameModel(id, commentModels);
                framesAL.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        FrameAdapter frameAdapter = new FrameAdapter(this, framesAL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        setContentView(R.layout.activity_post);
        framesRV = findViewById(R.id.frame_recycler_view);
        framesRV.setLayoutManager(linearLayoutManager);
        framesRV.setAdapter(frameAdapter);

        //TODO set onclick listeners for other things on this page here
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
        /*
        ImageButton likes_button = (ImageButton) findViewById(R.id.btn_likes);
        likes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), HomePage.class));
            }
        });
        */
        ImageButton profile_button = (ImageButton) findViewById(R.id.btn_profile);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfilePage.class));
            }
        });
    }
}

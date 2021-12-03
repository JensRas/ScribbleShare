package com.example.scribbleshare.postpage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.User;
import com.example.scribbleshare.activitypage.ActivityPage;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.homepage.HomePage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.profilepage.ProfilePage;
import com.example.scribbleshare.searchpage.SearchPage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Handles the UI for the post page
 */
public class PostPage extends AppCompatActivity implements PostView{
    private GetFramesPresenter getFramesPresenter;
    private NewFramePresenter newFramePresenter;
    private CommentPresenter commentPresenter;

    private RecyclerView framesRV;
    private ArrayList<FrameModel> framesAL;

    private String postId;
    private User localUser;

    Dialog dialog;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        localUser = MySingleton.getInstance(getApplicationContext()).getApplicationUser();

        getFramesPresenter = new GetFramesPresenter(this, getApplicationContext());
        newFramePresenter = new NewFramePresenter(this, getApplicationContext());
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.postId = bundle.getString("postId");
            getFramesPresenter.getFrames(postId);
        } else {
            //TODO show an error?
            Log.e("ERROR", "Bundle EMPTY when starting post page");
        }
        commentPresenter = new CommentPresenter();
        framesAL = new ArrayList<>(); //create an empty array list on page load for graceful empty list initially

        FrameAdapter frameAdapter = new FrameAdapter(this, framesAL);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        setContentView(R.layout.activity_post);
        framesRV = findViewById(R.id.frame_recycler_view);
        framesRV.setLayoutManager(linearLayoutManager);
        framesRV.setAdapter(frameAdapter);

        //TODO set onclick listeners for other things on this page here
        FloatingActionButton gif_button = (FloatingActionButton) findViewById(R.id.gif_button);
        gif_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debug", "clicked!");
                showDialog();
            }
        });

        Button new_frame_button = (Button) findViewById(R.id.new_frame_button);
        new_frame_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newFramePresenter.createNewFrame(localUser.getUsername(), postId, framesAL.size());
            }
        });

//        ImageButton like_button = (ImageButton) findViewById(R.id.post_like_button);
//        like_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Needs to be exact post > frame > comment
//                // Increment like count
//                TextView like_count = (TextView) findViewById(R.id.like_count);
//            }
//        });


        Button add_comment_button = (Button) findViewById(R.id.add_comment);
        add_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increment comment count

            }
        });

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

//        ImageButton activity_button = (ImageButton) findViewById(R.id.btn_activity);
//        activity_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(view.getContext(), ActivityPage.class));
//            }
//        });

        ImageButton profile_button = (ImageButton) findViewById(R.id.btn_profile);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfilePage.class));
            }
        });
    }

    @Override
    public void setFrames(JSONArray array) {
        Log.d("debug", "set frames in PostPage called");
        framesAL = new ArrayList<>();

        //iterate over the array and populate framesAL with new posts
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject obj = (JSONObject)array.get(i);
                int id = obj.getInt("id");
                JSONArray comments = obj.getJSONArray("comments");

                //get comment data inside of frame
                ArrayList<CommentModel> commentModels = new ArrayList<>();
                for(int j = 0; j < comments.length(); j++){
                    JSONObject commentObj = (JSONObject)comments.get(j);
                    int commentId = commentObj.getInt("id");
                    String commentProfileName = ((JSONObject)commentObj.get("user")).getString("username");
                    int likeCount = commentObj.getInt("likeCount");
                    commentModels.add(new CommentModel(commentId, commentProfileName, likeCount));
                }

                FrameModel m = new FrameModel(id, commentModels);
                framesAL.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("debug", "setting new frame adapter");
        FrameAdapter frameAdapter = new FrameAdapter(this, framesAL);
        framesRV.setAdapter(frameAdapter);
    }

    @Override
    public void refreshFrames() {
        Log.d("debug", "refreshFrames() called");
        getFramesPresenter.getFrames(postId);
    }

    private void showDialog() {
        // custom dialog
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_gif);

        ImageView gif = (ImageView) dialog.findViewById(R.id.gif);
        String url = EndpointCaller.baseURL + "/post/" + postId + "/gif";
        Glide.with(this)
                .load(url)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .into(gif);

        // set the custom dialog components - text, image and button
        ImageButton close = (ImageButton) dialog.findViewById(R.id.close_button);

        // Close Button
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO Close button action
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
    }
}

package com.example.scribbleshare.postpage;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.homepage.PostModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PostPage extends AppCompatActivity implements PostView{

    private PostPresenter postPresenter;
    private CommentPresenter commentPresenter;

    private RecyclerView framesRV;
    private ArrayList<FrameModel> framesAL;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        postPresenter = new PostPresenter();
        commentPresenter = new CommentPresenter();

    }

    @Override
    public void setFrames(JSONArray array) {
        framesAL = new ArrayList<>();

        //iterate over the array and populate postsAL with new posts
        for(int i = 0; i < array.length(); i++){
//            //TODO change this to reflect API data
//            try {
//
//                JSONObject obj = (JSONObject)array.get(i);
//                String id = obj.getString("id");
//                String profileName = obj.getString("username");
//                int likeCount = obj.getInt("likeCount");
//                int commentCount = obj.getInt("commentCount");
//                FrameModel m = new FrameModel(id, profileName, likeCount, commentCount);
//                framesRV.add(m);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
    }
}

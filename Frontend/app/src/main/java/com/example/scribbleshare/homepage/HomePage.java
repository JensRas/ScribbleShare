package com.example.scribbleshare.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.activitypage.ActivityPage;
import com.example.scribbleshare.drawingpage.DrawingPage;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.profilepage.ProfilePage;
import com.example.scribbleshare.searchpage.SearchPage;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Handles UI for the homepage and the buttons in the icon bar
 */
public class HomePage extends AppCompatActivity implements HomePageView{
    private RecyclerView postsRV;
    private ArrayList<PostModel> postsAL;

    private GetPostsPresenter postsPresenter;

    private WebSocketClient cc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postsPresenter = new GetPostsPresenter(this, getApplicationContext());
        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();
        postsPresenter.populateHomeScreenPosts(username); //when the request is done it calls "setHomePagePosts below
    }

    /**
     * This method sets the posts for the scrollable homepage
     * @param array Array of posts data
     */
    @Override
    public void setHomePagePosts(JSONArray array) {
        String username = MySingleton.getInstance(this).getApplicationUser().getUsername();
        Log.e("setHomePagePosts", "calling method");
        postsAL = new ArrayList<>();

        //iterate over the array and populate postsAL with new posts
        for(int i = 0; i < array.length(); i++){
            try {
                JSONObject obj = (JSONObject)array.get(i);
                String id = obj.getString("id");
                String profileName = ((JSONObject)obj.get("user")).getString("username");
                int likeCount = obj.getInt("likeCount");
                int commentCount = obj.getInt("commentCount");
                PostModel m = new PostModel(id, profileName, likeCount, commentCount);
                postsAL.add(m);
                //postsAL.get(i).setLikeCount(12);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // START WEBSOCKET FOR LIKES
        Draft[] drafts = {new Draft_6455()};

        // CONNECTIONS
        String s = EndpointCaller.baseURL.replace("http", "ws") + "/live/like/" + username;
//        String s = "ws://localhost:8080/live/like/" + username;

        //SENDING WEB SOCKET MESSAGES TO THE SERVER
        //the web socket is configured to send 3 different kinds of messages, r, +, or -
        //each message consists of the type (r,+,-) and a body, separated like a space
        //example; if I wanted to read the like counts of post 1, 2, and 3, I would send:
        //      "r 1,2,3"
        //example: if I wanted to increase the like count of post 2, I would send:
        //      "+ 2"
        //the same would be for un-liking a post, but to replace the "+" with a "-"
        //you cannot update the like count of multiple posts in one socket message

        //RECIEVING WEB SOCKET MESSAGES FROM THE SERVER
        //the web server responds with one format, a comma separated list of postId:likeCount
        //for example, if I sent the socket "r 2,3", I would get a response of
        //      2:423,3:49    <--- This means post 2 has 423 likes and post 3 has 49 likes
        //if the socket gets a request like the following:
        //      3:12,
        //this means post 3 needs to be updated to have a like count of 12

        //if there is ever a like count of -1, it means that the requested post couldn't be found on the server
        try {
            Log.d("Socket:", "Trying socket with url: " + s);
            cc = new WebSocketClient(new URI(s),(Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("SOCKET", "socket message returned: " + message);
                    //loop through the returned list, example: 2:423,3:49 means that post 2 and 3 should be updated to have like counts 423 and 49
                    String [] list = message.split(",");
                    for (int i = 0; i < list.length; i++) {
                        String[] post = list[i].split(":");
                        for (int j = 0; j < postsAL.size(); j++) {
                            if (postsAL.get(j).getId().equals(post[0])) {
                                postsAL.get(j).setLikeCount(Integer.parseInt(post[1]));
                            }
                        }
                    }
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    String str = "r";
                    Log.d("OPEN", "run() returned: " + "is connecting");
                    //once opened, THEN get posts here...
                    //this should be "r 1,2,3,4," where the numbers are a list of post ids that are on the home page
                    for(int i = 0; i < array.length(); i++){
                        try {
                            JSONObject obj = (JSONObject)array.get(i);
                            String id = obj.getString("id");
                            str += " " + id + ",";
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    cc.send(str);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("CLOSE", "onClose() returned: " + reason);
                }

                @Override
                public void onError(Exception e)
                {
                    Log.d("Exception:", e.toString());
                }
            };
        }
        catch (URISyntaxException e) {
            Log.d("Exception:", e.getMessage().toString());
            e.printStackTrace();
        }

        try {
            cc.connectBlocking();
        } catch (InterruptedException e) {
            Log.e("SOCKET", "connect blocking interrupted");
            e.printStackTrace();
        }
        //cc.send("+ 1");

        PostsAdapter adapterPost = new PostsAdapter(this, postsAL, cc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        setContentView(R.layout.activity_homepage);
        postsRV = findViewById(R.id.post_recycler_view);
        postsRV.setLayoutManager(linearLayoutManager);
        postsRV.setAdapter(adapterPost);

        // Icon buttons
        ImageButton home_button = (ImageButton) findViewById(R.id.btn_home);
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //already on home page
                //startActivity(new Intent(view.getContext(), HomePage.class));
            }
        });

        ImageButton search_button = (ImageButton) findViewById(R.id.btn_search);
        //search_button.setImageResource(android.R.drawable.ic_baseline_favorite_24);
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

        ImageButton activity_button = (ImageButton) findViewById(R.id.btn_activity);
        activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityPage.class));
            }
        });

        ImageButton profile_button = (ImageButton) findViewById(R.id.btn_profile);
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ProfilePage.class));
            }
        });
    }

}

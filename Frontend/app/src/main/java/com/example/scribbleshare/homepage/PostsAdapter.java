package com.example.scribbleshare.homepage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.createaccountpage.CreateAccount;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.network.MultipartRequestDownload;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder>{

    List<PostModel> postModels;
    Context context;

    public PostsAdapter(Context context, List<PostModel> postModels) {
        this.context = context;
        this.postModels = postModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_individual, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String profileName = postModels.get(position).getProfileName();
        String postId = postModels.get(position).getId();
        int likeCount = postModels.get(position).getLikeCount();
        int commentCount = postModels.get(position).getCommentCount();

        holder.profileName.setText(profileName);
        holder.likeCount.setText(likeCount + "");
        holder.commentCount.setText(commentCount + "");

        String imageUrl = EndpointCaller.baseURL + "/post/" + postId + "/image";
        Glide.with(context).load(imageUrl).into(holder.scribble);

        //TODO set holder.thing.setOnClickListeners here
        holder.scribble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO when the image is clicked

                //TODO remove this its just for testing
                Log.e("test", "username: " + MySingleton.getInstance(context).getApplicationUser().getUsername());
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO when like button clicked
            }
        });

        holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO when comment button is clicked
            }
        });
    }

    @Override
    public int getItemCount() {
        return postModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView scribble;
        TextView profileName, likeCount, commentCount;
        ImageButton likeButton, commentButton, shareButton;
        //TODO add other buttons/textviews/etc

        public Holder(@NonNull View itemView) {
            super(itemView);
            scribble = itemView.findViewById(R.id.post);
            profileName = itemView.findViewById(R.id.profile_name);
            likeCount = itemView.findViewById(R.id.post_like_count);
            commentCount = itemView.findViewById(R.id.post_comment_count);
            likeButton = itemView.findViewById(R.id.post_like_button);
            commentButton = itemView.findViewById(R.id.post_comment_button);
            shareButton = itemView.findViewById(R.id.post_share_button);
        }
    }
}

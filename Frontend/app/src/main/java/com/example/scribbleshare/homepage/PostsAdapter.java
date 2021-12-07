package com.example.scribbleshare.homepage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.postpage.PostPage;
import com.example.scribbleshare.profilepage.OtherProfilePage;
import com.example.scribbleshare.profilepage.ProfilePage;

import org.java_websocket.client.WebSocketClient;

import java.util.List;

/**
 * Handles the RecyclerView for the homepage
 */
public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder>{
    List<PostModel> postModels;
    Context context;
    WebSocketClient websocket;

    /**
     * Constructor to initialize post models for the homepage
     * @param context Current context
     * @param postModels List of post models for the homepage
     */
    public PostsAdapter(Context context, List<PostModel> postModels) {
        this.context = context;
        this.postModels = postModels;
    }

    public void setWebsocket(WebSocketClient websocket){
        this.websocket = websocket;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_homepage_post, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        PostModel model = postModels.get(position);
        String profileName = model.getProfileName();
        int postId = model.getId();
        int likeCount = model.getLikeCount();

        holder.profileName.setText(profileName);
        holder.likeCount.setText(likeCount + "");

        String imageUrl = EndpointCaller.baseURL + "/post/" + postId + "/image";
        Glide.with(context)
                .load(imageUrl)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .into(holder.scribble);

        if (model.getIsLiked()) {
            holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        holder.scribble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostPage.class);
                intent.putExtra("postId", postId);
                context.startActivity(intent);
            }
        });

        holder.profileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileName.equals(MySingleton.getInstance(context).getApplicationUser().getUsername())){
                    Intent intent = new Intent(context, ProfilePage.class);
                    context.startActivity(intent);
                } else{
                    Intent intent = new Intent(context, OtherProfilePage.class);
                    intent.putExtra("username", profileName);
                    context.startActivity(intent);}
            }
        });

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getIsLiked()) {
                    holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    model.setIsLiked(false);
                    websocket.send("- " + postId);
                } else {
                    holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_24);
                    model.setIsLiked(true);
                    websocket.send("+ " + postId);
                }
            }
        });

        holder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostPage.class);
                intent.putExtra("postId", postId);
                context.startActivity(intent);
            }
        });
    }

    /**
     * This method returns the item count inside the RecyclerView
     * @return Item count inside the RecyclerView
     */
    @Override
    public int getItemCount() {
        return postModels.size();
    }

    /**
     * RecyclerView ViewHolder necessary for homepage post
     */
    class Holder extends RecyclerView.ViewHolder {
        ImageView scribble;
        TextView profileName, likeCount;
        ImageButton likeButton, commentButton;

        /**
         * Constructor to initialize necessary information for a homepage post
         * @param itemView Current item view
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            scribble = itemView.findViewById(R.id.post);
            profileName = itemView.findViewById(R.id.profile_name);
            likeCount = itemView.findViewById(R.id.post_like_count);
            likeButton = itemView.findViewById(R.id.post_like_button);
            commentButton = itemView.findViewById(R.id.post_comment_button);
        }
    }
}

package com.example.scribbleshare.profilepage;

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
import com.example.scribbleshare.R;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.postpage.PostPage;
import com.example.scribbleshare.homepage.PostModel;

import java.util.List;

/**
 * Handles the RecyclerView for the homepage
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.Holder>{
    List<PostModel> postModels;
    Context context;

    /**
     * Constructor to initialize post models for the homepage
     * @param context Current context
     * @param postModels List of post models for the homepage
     */
    public ProfileAdapter(Context context, List<PostModel> postModels) {
        this.context = context;
        this.postModels = postModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_post_fragment, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
       String profileName = postModels.get(position).getProfileName();
       int postId = postModels.get(position).getId();
       int likeCount = postModels.get(position).getLikeCount();
       int commentCount = postModels.get(position).getCommentCount();

        holder.profileName.setText(profileName);
        holder.likeCount.setText(likeCount + "");
        holder.commentCount.setText(commentCount + "");

        String imageUrl = EndpointCaller.baseURL + "/post/" + postId + "/image";
        Glide.with(context)
                .load(imageUrl)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .into(holder.scribble);

        //TODO set holder.thing.setOnClickListeners here
        holder.scribble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO when the image is clicked
                Intent intent = new Intent(context, PostPage.class);
                intent.putExtra("postId", postId);
                context.startActivity(intent);
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
        TextView profileName, likeCount, commentCount;
        ImageButton likeButton, commentButton, shareButton;

        /**
         * Constructor to initialize necessary information for a homepage post
         * @param itemView Current item view
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            scribble = itemView.findViewById(R.id.test_image);
            profileName = itemView.findViewById(R.id.profile_name);
            likeCount = itemView.findViewById(R.id.post_like_count);
            commentCount = itemView.findViewById(R.id.post_comment_count);
            likeButton = itemView.findViewById(R.id.post_like_button);
            commentButton = itemView.findViewById(R.id.post_comment_button);
            shareButton = itemView.findViewById(R.id.post_share_button);
        }
    }
}

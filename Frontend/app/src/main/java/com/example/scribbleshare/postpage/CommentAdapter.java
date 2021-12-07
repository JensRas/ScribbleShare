package com.example.scribbleshare.postpage;

import android.content.Context;
import android.util.Log;
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

import java.util.List;


/**
 * Handles the RecyclerView for the comments on a post
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder>{
    List<CommentModel> commentModels;
    Context context;

    /**
     * Constructor to initialize comment models for the post
     * @param context Current context
     * @param commentModels List of comment models for the post
     */
    public CommentAdapter(Context context, List<CommentModel> commentModels){
        this.context = context;
        this.commentModels = commentModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_comment, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String profileName = commentModels.get(position).getProfileName();
        int likeCount = commentModels.get(position).getLikeCount();

        holder.likeCount.setText(likeCount + "");
        holder.profileName.setText(profileName);

        //load comment image
        String imageUrl = EndpointCaller.baseURL + "/comment/" + commentModels.get(position).getId() + "/image";
        Glide.with(context)
                .load(imageUrl)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .into(holder.commentScribble);

        //TODO set onclick listeners for comment stuff here
        if (commentModels.get(holder.getAdapterPosition()).getIsLiked()) {
            holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_24);
        } else {
            holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentModels.get(holder.getAdapterPosition()).getIsLiked()) {
                    holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    commentModels.get(holder.getAdapterPosition()).setIsLiked(false);
                    holder.likeCount.setText(commentModels.get(holder.getAdapterPosition()).getLikeCount() - 1 + "");
                    commentModels.get(holder.getAdapterPosition()).setLikeCount(commentModels.get(holder.getAdapterPosition()).getLikeCount() - 1);
                    Log.d("liking", "unliked");
                    //websocket.send("- " + postId);
                } else {
                    holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_24);
                    commentModels.get(holder.getAdapterPosition()).setIsLiked(true);
                    holder.likeCount.setText(commentModels.get(holder.getAdapterPosition()).getLikeCount() + 1 + "");
                    commentModels.get(holder.getAdapterPosition()).setLikeCount(commentModels.get(holder.getAdapterPosition()).getLikeCount() + 1);
                    Log.d("liking", "liked");
                    //websocket.send("+ " + postId);
                }
            }
        });
    }

    /**
     * This method returns the item count of the comment models
     * @return Item count of the comment models
     */
    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    /**
     * RecyclerView ViewHolder necessary for the post's comments
     */
    class Holder extends RecyclerView.ViewHolder {
        ImageView commentScribble;
        TextView likeCount, profileName;
        ImageButton likeButton;
        //TODO add other buttons/textviews/etc

        /**
         * Constructor to initialize necessary information for a post's comments
         * @param itemView Current item view
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            commentScribble = itemView.findViewById(R.id.frame_image);
            likeCount = itemView.findViewById(R.id.like_count);
            profileName = itemView.findViewById(R.id.profile_name);
            likeButton = itemView.findViewById(R.id.post_like_button);
        }
    }
}

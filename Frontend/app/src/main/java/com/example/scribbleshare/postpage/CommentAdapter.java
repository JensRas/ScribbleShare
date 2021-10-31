package com.example.scribbleshare.postpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.scribbleshare.R;
import com.example.scribbleshare.network.EndpointCaller;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.Holder>{

    List<CommentModel> commentModels;
    Context context;

    public CommentAdapter(Context context, List<CommentModel> commentModels){
        this.context = context;
        this.commentModels = commentModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_fragment, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String profileName = commentModels.get(position).getProfileName();
        int likeCount = commentModels.get(position).getLikeCount();

        holder.likeCount.setText(likeCount + "");
        holder.profileName.setText(profileName);

        //TODO implement once the proper endpoint has been made :D
        String imageUrl = EndpointCaller.baseURL + "/comment/" + commentModels.get(position).getId() + "/image";
        Glide.with(context).load(imageUrl).into(holder.commentScribble);

        //TODO set onclick listeners for comment stuff here
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView commentScribble;
        TextView likeCount, profileName;
        //TODO add other buttons/textviews/etc

        public Holder(@NonNull View itemView) {
            super(itemView);
            commentScribble = itemView.findViewById(R.id.frame_image);
            likeCount = itemView.findViewById(R.id.like_count);
            profileName = itemView.findViewById(R.id.profile_name);
        }
    }
}

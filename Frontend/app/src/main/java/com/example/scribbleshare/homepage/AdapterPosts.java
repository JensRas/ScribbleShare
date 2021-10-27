package com.example.scribbleshare.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.R;

import java.util.List;

public class AdapterPosts extends RecyclerView.Adapter<AdapterPosts.Holder>{

    List<PostModel> postModels;
    Context context;

    public AdapterPosts(Context context, List<PostModel> postModels) {
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

        holder.profileName.setText(profileName);

        //TODO set holder.thing.setOnClickListener here
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

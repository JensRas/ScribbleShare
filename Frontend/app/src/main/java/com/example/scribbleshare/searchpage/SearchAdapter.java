package com.example.scribbleshare.searchpage;

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
import com.example.scribbleshare.R;
import com.example.scribbleshare.homepage.PostModel;
import com.example.scribbleshare.network.EndpointCaller;
import com.example.scribbleshare.postpage.PostPage;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.Holder> {
    List<SearchModel> searchModels;
    Context context;

    /**
     * Constructor to initialize post models for the search page
     *
     * @param context      Current context
     * @param searchModels List of post models for the search page
     */
    public SearchAdapter(Context context, List<SearchModel> searchModels) {
        this.context = context;
        this.searchModels = searchModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_fragment, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String username = searchModels.get(position).getUsername();
        int followerCount = searchModels.get(position).getFollowerCount();

        holder.username.setText(username);
        holder.followerCount.setText(followerCount + "");

        //TODO set holder.thing.setOnClickListeners here
        //TODO maybe just set the whole view as an on click instead?
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
    }

    /**
     * This method returns the item count inside the RecyclerView
     *
     * @return Item count inside the RecyclerView
     */
    @Override
    public int getItemCount() {
        return searchModels.size();
    }

    /**
     * RecyclerView ViewHolder necessary for a search result
     */
    class Holder extends RecyclerView.ViewHolder {
        TextView username, followerCount;

        /**
         * Constructor to initialize necessary information for a search result
         *
         * @param itemView Current item view
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.profile_name);
            followerCount = itemView.findViewById(R.id.follower_count);
        }
    }
}
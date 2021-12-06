package com.example.scribbleshare.postpage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.MySingleton;
import com.example.scribbleshare.R;
import com.example.scribbleshare.User;
import com.example.scribbleshare.drawingpage.DrawingPage;

import java.util.List;

/**
 * Handles the RecyclerView for the frames of a post
 */
public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.Holder> {
    List<FrameModel> frameModels;
    Context context;
    View view;
    NewFramePresenter newFramePresenter;
    int postId;

    /**
     * Constructor to initialize necessary information for a frame
     * @param context Current context
     * @param frameModels List of model frames
     */
    public FrameAdapter(Context context, List<FrameModel> frameModels, NewFramePresenter newFramePresenter, int postId){
        this.context = context;
        this.frameModels = frameModels;
        this.newFramePresenter = newFramePresenter;
        this.postId = postId;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if(viewType == R.layout.fragment_frame){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_frame, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.button_new_frame, parent, false);
        }
        return new FrameAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(position == frameModels.size()) {
            holder.createFrameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User localUser = MySingleton.getInstance(context).getApplicationUser();
                    newFramePresenter.createNewFrame(localUser.getUsername(), postId + "", frameModels.size(), true);
                }
            });
        } else {
            int frameId = frameModels.get(position).getFrameId();

            holder.frameNumber.setText("Frame: " + (position + 1));

            CommentAdapter commentAdapter = new CommentAdapter(context, frameModels.get(position).getComments());
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.commentRV.setLayoutManager(linearLayoutManager);
            holder.commentRV.setAdapter(commentAdapter);

            /*
            if (frameModels.getIsLiked()) {
                holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_24);
            } else {
                holder.likeButton.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            }

            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (frameModels.getIsLiked()) {
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
            */

            //TODO add listener for clickable stuff here
            holder.createCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DrawingPage.class);
                    intent.putExtra("drawContext", "newComment");
                    Log.d("debug", "switching to Drawing page and setting postId: " + postId);
                    intent.putExtra("postId", postId);
                    intent.putExtra("frameId", frameId);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return (position == frameModels.size()) ? R.id.add_frame_button : R.layout.fragment_frame;
    }

    /**
     * This method returns the item count inside the RecyclerView
     * @return Item count inside the RecyclerView
     */
    @Override
    public int getItemCount() {
        return frameModels.size() + 1; // + 1 because of the extra button added to the end
    }

    /**
     * RecyclerView ViewHolder necessary for a frame of a post
     */
    class Holder extends RecyclerView.ViewHolder {
        TextView frameNumber;
        Button createCommentButton, createFrameButton;
        RecyclerView commentRV;

        /**
         * Constructor to initialize necessary information for a frame of a post
         * @param itemView Current item view
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            frameNumber = itemView.findViewById(R.id.frame_number);
            createCommentButton = itemView.findViewById(R.id.add_comment);
            commentRV = itemView.findViewById(R.id.comment_recycler_view);
            createFrameButton = itemView.findViewById(R.id.add_frame_button);
        }
    }
}

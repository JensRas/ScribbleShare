package com.example.scribbleshare.postpage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.R;
import com.example.scribbleshare.drawingpage.DrawingPage;

import java.util.List;

/**
 * Handles the RecyclerView for the frames of a post
 */
public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.Holder> {
    List<FrameModel> frameModels;
    Context context;

    /**
     * Constructor to initialize necessary information for a frame
     * @param context Current context
     * @param frameModels List of model frames
     */
    public FrameAdapter(Context context, List<FrameModel> frameModels){
        this.context = context;
        this.frameModels = frameModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_frame, parent, false);
        return new FrameAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int frameId = frameModels.get(position).getFrameId();

        holder.frameNumber.setText("Frame: " + (position + 1));

        CommentAdapter commentAdapter = new CommentAdapter(context, frameModels.get(position).getComments());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.commentRV.setLayoutManager(linearLayoutManager);
        holder.commentRV.setAdapter(commentAdapter);

        //TODO add listener for clickable stuff here
        holder.createCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DrawingPage.class);
                intent.putExtra("drawContext", "newComment");
                intent.putExtra("frameId", frameId);
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
        return frameModels.size();
    }

    /**
     * RecyclerView ViewHolder necessary for a frame of a post
     */
    class Holder extends RecyclerView.ViewHolder {
        TextView frameNumber;
        Button createCommentButton;
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
        }
    }
}

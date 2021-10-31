package com.example.scribbleshare.postpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scribbleshare.R;
import com.example.scribbleshare.homepage.PostsAdapter;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.Holder> {

    List<FrameModel> frameModels;
    Context context;

    public FrameAdapter(Context context, List<FrameModel> frameModels){
        this.context = context;
        this.frameModels = frameModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frame_fragment, parent, false);
        return new FrameAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        int frameId = frameModels.get(position).getFrameId();
        ArrayList<CommentModel> commentModels = frameModels.get(position).getComments();

        holder.frameNumber.setText("Frame: " + (position + 1));

        CommentAdapter commentAdapter = new CommentAdapter(context, frameModels.get(position).getComments());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.commentRV.setLayoutManager(linearLayoutManager);
        holder.commentRV.setAdapter(commentAdapter);

        //TODO add listener for clickable stuff here
        holder.createCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return frameModels.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView frameNumber;
        Button createCommentButton;
        RecyclerView commentRV;

        public Holder(@NonNull View itemView) {
            super(itemView);
            frameNumber = itemView.findViewById(R.id.frame_number);
            createCommentButton = itemView.findViewById(R.id.add_comment);
            commentRV = itemView.findViewById(R.id.comment_recycler_view);
        }
    }
}

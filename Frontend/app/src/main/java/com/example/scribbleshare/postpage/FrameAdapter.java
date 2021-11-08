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
 *
 */
public class FrameAdapter extends RecyclerView.Adapter<FrameAdapter.Holder> {
    List<FrameModel> frameModels;
    Context context;

    /**
     *
     * @param context
     * @param frameModels
     */
    public FrameAdapter(Context context, List<FrameModel> frameModels){
        this.context = context;
        this.frameModels = frameModels;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.frame_fragment, parent, false);
        return new FrameAdapter.Holder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
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
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return frameModels.size();
    }

    /**
     *
     */
    class Holder extends RecyclerView.ViewHolder {
        TextView frameNumber;
        Button createCommentButton;
        RecyclerView commentRV;

        /**
         *
         * @param itemView
         */
        public Holder(@NonNull View itemView) {
            super(itemView);
            frameNumber = itemView.findViewById(R.id.frame_number);
            createCommentButton = itemView.findViewById(R.id.add_comment);
            commentRV = itemView.findViewById(R.id.comment_recycler_view);
        }
    }
}

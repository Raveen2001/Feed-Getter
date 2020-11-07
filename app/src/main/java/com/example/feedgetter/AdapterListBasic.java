package com.example.feedgetter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class AdapterListBasic extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PostData> items = new ArrayList<>();
    private Context ctx;

    public AdapterListBasic(Context context, List<PostData> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name, description;
        public TextView likeCount, commentCount, shareCount;
        public ImageView like, share, comment;
        public LinearLayout likeContainer, commentContainer, shareContainer;
        public boolean isLiked, isCommented, isShared;

        public OriginalViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            description = v.findViewById(R.id.description);
            like = v.findViewById(R.id.like);
            likeCount = v.findViewById(R.id.likeCount);
            comment = v.findViewById(R.id.comment);
            commentCount = v.findViewById(R.id.commentCount);
            share = v.findViewById(R.id.share);
            shareCount = v.findViewById(R.id.shareCount);
            likeContainer = v.findViewById(R.id.likeContainer);
            commentContainer = v.findViewById(R.id.commentContainer);
            shareContainer = v.findViewById(R.id.shareContainer);
            likeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int likeInt, position = getAdapterPosition();
                    if(!isLiked) {
                        likeInt = ++items.get(position).likes;
                        likeCount.setText(String.valueOf(likeInt));
                        like.setColorFilter(Color.RED);
                        Toast.makeText(ctx,  "Liked", Toast.LENGTH_SHORT).show();
                        isLiked = true;
                    }else{
                        likeInt = --items.get(position).likes;
                        likeCount.setText(String.valueOf(likeInt));
                        like.setColorFilter(Color.GRAY);
                        isLiked = false;
                    }
                }
            });

            commentContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int commentInt, position = getAdapterPosition();
                    if(!isCommented){
                        commentInt = ++items.get(position).comment;
                        commentCount.setText(String.valueOf(commentInt));
                        comment.setColorFilter(Color.YELLOW);
                        Toast.makeText(ctx, "Commented", Toast.LENGTH_SHORT).show();
                        isCommented = true;
                    }else{
                        commentInt = --items.get(position).comment;
                        commentCount.setText(String.valueOf(commentInt));
                        comment.setColorFilter(Color.GRAY);
                        isCommented = false;
                    }
                }
            });

            shareContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int shareInt, position = getAdapterPosition();
                    if(!isShared){
                        shareInt = ++items.get(position).share;
                        shareCount.setText(String.valueOf(shareInt));
                        share.setColorFilter(Color.GREEN);
                        Toast.makeText(ctx, "Shared", Toast.LENGTH_SHORT).show();
                        isShared = true;
                    }else{
                        shareInt = --items.get(position).share;
                        shareCount.setText(String.valueOf(shareInt));
                        share.setColorFilter(Color.GRAY);
                        isShared = false;
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_card, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            PostData p = items.get(position);
            view.name.setText(p.name);
            Glide.with(ctx)
                    .load(p.image)
                    .into(view.image);
            view.description.setText(p.description);
            view.commentCount.setText(String.valueOf(p.comment));
            view.likeCount.setText(String.valueOf(p.likes));
            view.shareCount.setText(String.valueOf(p.share));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
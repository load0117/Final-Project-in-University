package com.example.twolee.chatbot.ReviewWrite;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;

public class ReviewHolder extends RecyclerView.ViewHolder {
    private TextView reviewScreen;
    private ImageButton likeButton;

    public ReviewHolder(View itemview){
        super(itemview);

        setIds(itemview);
        setListener(itemview);
    }

    public void setIds(View itemview){
        reviewScreen = itemview.findViewById(R.id.review_screen);
        likeButton = itemview.findViewById(R.id.review_likeBtn);
    }

    public void setListener(View itemview){
        reviewScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("디버깅","success?");
            }
        });
    }
}

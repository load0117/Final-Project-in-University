package com.example.twolee.chatbot.ReviewWrite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twolee.chatbot.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {

    //타입 분류
    private static final int VIEW_TYPE_PROFILE = 0;
    private static final int VIEW_TYPE_REVIEW = 1;

    private List<Review> reviewArrayList;

    // 생성자
    public ReviewAdapter(ArrayList<Review> reviewArrayList){
        this.reviewArrayList = reviewArrayList;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //뷰 홀더를 어떻게 생성해서 무엇을 전달할 것인가.
        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_show,parent,false);
        // type에 상관없이 리뷰만 보여주기

        return new ReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        //한줄 한줄의 아이템에 holder를 통해 ui에 접근해서 데이터를 그려주는 것
        Review review = reviewArrayList.get(position);

    }
    /*
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //한줄 한줄의 아이템에 holder를 통해 ui에 접근해서 데이터를 그려주는 것
        Review review = reviewArrayList.get(position);


    }
    */

    @Override
    public int getItemCount() {
        //아이템의 개수를 구하는 부분
        return reviewArrayList.size();
    }
}

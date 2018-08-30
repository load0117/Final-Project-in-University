package com.example.twolee.chatbot.ReviewWrite;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    // 데이터 관리 역할. 디비와 어뎁터 연결.

    private List<Review> reviewArrayList;

    // 생성자
    public ReviewAdapter(ArrayList<Review> reviewArrayList){
        this.reviewArrayList = reviewArrayList;
        createView();
    }

    // 호출 순서 - getItemCount , onCreateViewHolder, onBindViewHolder
    @Override
    public int getItemCount() {
        //아이템의 개수를 구하는 부분
        return reviewArrayList.size();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 뷰 홀더 생성시 호출 어떻게 생성해서 무엇을 전달할 것인가. xml layout 을 뷰 홀더로 세팅하는 것
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_show,parent,false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(itemView);
        // type에 상관없이 리뷰만 보여주기
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        // 데이터를 알맞게 새로 세팅하는 것. 데이터 변경시 계속 호출.
        // TODO: 2018. 8. 30. 디비에서 데이터 적용하기 
        Review review = reviewArrayList.get(position);
        TextView textView = holder.itemView.findViewById(R.id.review_id_show);
        textView.setText(position+"");
        textView = holder.itemView.findViewById(R.id.review_screen);
        textView.setText("현재 :"+(position+1));
        ImageView imageView = holder.itemView.findViewById(R.id.profile_icon);
        imageView.setImageResource(R.drawable.ic_bot);

    }
    //디버그 용
    public void createView(){
        // TODO: 2018. 8. 30. 화면에 보여주기 적당한 숫자 찾기
        int num = 6;
        for(int i=0; i<num; i++){
            Review review = new Review((i+1)+"","현재 :"+(i+1),(i%num));
            reviewArrayList.add(review);
        }
    }


    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        //여기서 홀더 관리.
        private TextView reviewScreen;
        private ImageButton likeButton;

        public ReviewViewHolder(View itemview){
            super(itemview);

            setIds(itemview);
            setListener(itemview);
        }

        public void setIds(View itemview){
            reviewScreen = itemview.findViewById(R.id.review_screen);
            likeButton = itemview.findViewById(R.id.review_likeBtn);
        }

        public void setListener(final View itemview){
            reviewScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 시 -> 리뷰 페이지로 이동
                    Intent intent = new Intent(v.getContext(),ReviewShow.class);
                    itemview.getContext().startActivity(intent);
                    Log.w("select", "화면 누름");
                }
            });

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018. 8. 30. 버튼 누를 시 디비와 연동
                    Toast.makeText(v.getContext().getApplicationContext(), "됐다!.",Toast.LENGTH_SHORT).show();
                    Log.w("디버깅","버튼 누름");
                }
            });
        }
    }
}

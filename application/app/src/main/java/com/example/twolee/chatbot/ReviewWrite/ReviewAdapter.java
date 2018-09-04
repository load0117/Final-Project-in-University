package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    // 데이터 관리 역할. 디비와 어뎁터 연결.
    private DatabaseReference myref = FirebaseDatabase.getInstance().getReference();

    private List<Review> reviewArrayList;
    private Review reviewRef;
    // 생성자
    public ReviewAdapter(ArrayList<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
        //changeView();
        createView();
        //디버깅


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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_show, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(itemView);
        // type에 상관없이 리뷰만 보여주기
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {
        // 데이터를 알맞게 새로 세팅하는 것. 데이터 변경시 계속 호출.
        // TODO: 2018. 9. 3. 디비에서 뷰 불러오기
        /*
            디비에서 값 읽어 오기.
        */

        reviewRef= reviewArrayList.get(position);

        // TODO: 2018. 9. 3. 경로 제대로 설정 하기
        myref.child("reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                    String reviewNum = dataSnapshot1.getKey();
                    //System.out.println(reviewNum);
                    String id = dataSnapshot1.child("id").getValue(String.class);
                    String contents = dataSnapshot1.child("contents").getValue(String.class);
                    String rating = dataSnapshot1.child("rating").getValue(String.class);
                    String writtenTime = dataSnapshot1.child("writtenTime").getValue(String.class);

                    reviewRef.setReviewNum(reviewNum);
                    reviewRef.setId(id);
                    reviewRef.setContents(contents);
                    reviewRef.setRating(rating);
                    reviewRef.setWrittenTime(writtenTime);
                }
            };

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        TextView textView = holder.itemView.findViewById(R.id.review_screen);
        textView.setText(reviewRef.getContents());
        textView = holder.itemView.findViewById(R.id.review_id_show);
        textView.setText(reviewRef.getId());

        // 뷰를 만들고 (어뎁터- why? 리스트에 연결 하려고.) -> 디비에서 값을 받아와서(어댑터에서 -언제?처음, 스크롤 내릴 시.) -> 뷰에 집어 넣기. (어댑터 why? 뷰가 있으니깐)
    }

    public void createView() {
        int num = 6;
        for(int i=0; i<num; i++){
            Review review = new Review();
            reviewArrayList.add(review);
        }
    }


    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        //여기서 홀더 관리.
        private TextView reviewScreen;
        private ImageButton likeButton;

        private DatabaseReference myref = FirebaseDatabase.getInstance().getReference();

        public ReviewViewHolder(View itemview) {
            super(itemview);

            setIds(itemview);
            setListener(itemview);
        }

        public void setIds(View itemview) {
            reviewScreen = itemview.findViewById(R.id.review_screen);
            likeButton = itemview.findViewById(R.id.review_likeBtn);
        }

        public void setListener(final View itemview) {
            reviewScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 시 -> 리뷰 페이지로 이동
                    Intent intent = new Intent(v.getContext(), ReviewShow.class);
                    itemview.getContext().startActivity(intent);
                    Log.w("select", "화면 누름");
                }
            });

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018. 8. 30. 버튼 누를 시 디비와 연동 - 리뷰 넘버를 알고선 추가.
                    Toast.makeText(v.getContext().getApplicationContext(), "됐다!.", Toast.LENGTH_SHORT).show();
                    Log.w("디버깅", "버튼 누름");
                }
            });
        }
    }
}

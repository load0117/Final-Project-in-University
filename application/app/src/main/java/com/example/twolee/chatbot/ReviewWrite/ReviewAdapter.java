package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    // 데이터 관리 역할. 디비와 어뎁터 연결.
    private List<Review> reviewArrayList;
    private List<String> review_uidList;

    // 생성자
    public ReviewAdapter(List<Review> reviewArrayList, List<String> review_uidList) {
        this.reviewArrayList = reviewArrayList;
        this.review_uidList = review_uidList;
    }

    // 호출 순서 - getItemCount , onCreateViewHolder, onBindViewHolder
    @Override
    public int getItemCount() {
        //아이템의 개수를 구하는 부분
        return reviewArrayList.size();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 뷰 홀더 생성시 호출 어떻게 생성해서 무엇을 전달할 것인가. 새롭게 생성할 때만 호출된다.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_show, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        // 데이터를 알맞게 새로 세팅하는 것. 데이터 변경시 계속 호출.

        // 홈 프래그먼트가 담고 있는 정보를 어댑터로 가져오기. 어댑터에서 리사이클러 뷰로 정보 띄우기
        Review review = reviewArrayList.get(position);
        String review_uid = review_uidList.get(position);
        holder.setData(review, review_uid);
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        //        //여기서 홀더 관리.
        @BindView(R.id.profile_show) ImageView profile_show;
        @BindView(R.id.review_userEmail) TextView review_userEmail;
        @BindView(R.id.review_user_uid) TextView review_user_uid;
        @BindView(R.id.review_uid) TextView review_uid;
        @BindView(R.id.review_more) ImageView review_more;
        @BindView(R.id.review_contents) TextView review_contents;
        @BindView(R.id.review_rating) TextView review_rating;
        @BindView(R.id.review_like) TextView review_like;
        @BindView(R.id.review_writtenTime) TextView review_writtenTime;
        @BindView(R.id.review_likeBtn)
        ImageButton review_likeBtn;

        // database reference
        private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        private boolean isLike = false;

        public ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            setListener();
        }

        // view data
        public void setData(Review review, String uid){
            //profile
            review_userEmail.setText(review.getUsername());
            review_uid.setText(uid);
            review_user_uid.setText(review.getUserUid());
            review_contents.setText(review.getContents());

            review_rating.setText(review.getRating());
            review_like.setText(String.valueOf(review.getLike()));
            review_writtenTime.setText(review.getWrittenTime());
        }


        public void setListener() {
            // click more button

            review_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(itemView.getContext().getApplicationContext(), "더보기 기능 추가하기.", Toast.LENGTH_SHORT).show();
                }
            });


            // click review isLike button
            review_likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    long likes = Long.valueOf(review_like.getText().toString());

                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                    // 현재 로그인 돼 있지 않으면 로그인 페이지로.
                    if(currentUser == null){
                        Intent intent = new Intent(v.getContext().getApplicationContext(), LoginActivity.class);
                        v.getContext().startActivity(intent);
                    }else{
                        // 좋아요 되어 있는지 확인 후 선택.

                        if(isLike){
                            likes--;
                        }else{
                            likes++;
                        }
                        /*
                        if(isLike){
                            // 좋아요 클릭 취소
                            //System.out.println("좋아요 취소!");
                            likes--;
                            isLike = false;
                            review_likeBtn.setImageResource(R.drawable.ic_like_icon);
                        }else{
                            // 좋아요 클릭
                            //System.out.println("좋아요!");
                            likes++;
                            isLike = true;
                            review_likeBtn.setImageResource(R.drawable.ic_like_selected_icon);
                        }

                        // 좋아요 화면에 반영.
                        review_like.setText(String.valueOf(likes));

                        // 디비에 반영.
                        myRef.child("reviews").child(review_uid.getText().toString()).child("like").setValue(likes);

                        */
                        review_like.setText(String.valueOf(likes));

                        // 디비에 반영.
                        myRef.child("reviews").child(review_uid.getText().toString()).child("like").setValue(likes);
                    }
                }
            });
        }
    }
}

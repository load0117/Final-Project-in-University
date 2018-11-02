package com.example.twolee.chatbot.mainFragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.twolee.chatbot.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        //@BindView(R.id.review_more) ImageView review_more;
        @BindView(R.id.review_contents) TextView review_contents;
        @BindView(R.id.review_rating) TextView review_rating;
        @BindView(R.id.review_writtenTime) TextView review_writtenTime;

        // database reference
        private static StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();
        private View view;

        private ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            this.view = view;
        }

        // view data
        private void setData(Review review, String uid){
            // 보이는 부분
            storage.child("profile").child(review.getUserUid()).getDownloadUrl()
                    .addOnSuccessListener( (uri) -> Glide.with(view.getContext().getApplicationContext()).load(uri).into(profile_show))
                    .addOnFailureListener((e) -> Log.w("fail","불러오기 실패"));

            //profile
            review_userEmail.setText(review.getUsername());
            review_contents.setText(review.getContents());
            review_writtenTime.setText(review.getWrittenTime());
            review_rating.setText(review.getRating());

            // 보이지 않는 부분
            review_uid.setText(uid);
            review_user_uid.setText(review.getUserUid());
        }



        @OnClick(R.id.profile_show)
        public void profile(View v){
            // 수정하기
            Intent intent = new Intent(v.getContext().getApplicationContext(), ProfileShowActivity.class);
            intent.putExtra("userUid", review_user_uid.getText().toString());
            v.getContext().startActivity(intent);
        }

        @OnClick(R.id.review_more)
        public void moreInfo(){
            Toast.makeText(itemView.getContext().getApplicationContext(), "더보기 기능 추가하기.", Toast.LENGTH_SHORT).show();
        }
    }
}

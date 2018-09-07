package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    // 데이터 관리 역할. 디비와 어뎁터 연결.
    private List<Review> reviewArrayList;

    // 생성자
    public ReviewAdapter(List<Review> reviewArrayList) {
        this.reviewArrayList = reviewArrayList;
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
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        // 데이터를 알맞게 새로 세팅하는 것. 데이터 변경시 계속 호출.

        // TODO: 2018. 9. 3. 디비에서 뷰에 값만 넣기.
        // 보여지고 있는 뷰의 위치 가져오기.
        Review review = reviewArrayList.get(position);
        /* 디비에서 리뷰로 값 가져오기 */
        holder.setData(review);
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        //        //여기서 홀더 관리.
        @BindView(R.id.profile_show) ImageView profile_show;
        @BindView(R.id.review_id_show) TextView review_id_show;
        @BindView(R.id.review_uid) TextView review_uid;
        @BindView(R.id.review_more) ImageView review_more;
        @BindView(R.id.review_screen) TextView review_screen;
        @BindView(R.id.review_rating) TextView review_rating;
        @BindView(R.id.review_like) TextView review_like;
        @BindView(R.id.review_writtenTime) TextView review_writtenTime;
        @BindView(R.id.review_likeBtn)
        ImageButton review_likeBtn;

        // database reference
        private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        private boolean like = false;

        public ReviewViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            //non see uid.
            review_uid.setVisibility(View.INVISIBLE);

            setListener();
        }

        // view data
        public void setData(Review review){
            review_id_show.setText(review.getUsername());
            review_uid.setText(review.getUid());
            review_screen.setText(review.getContents());
            review_rating.setText(review.getRating());
            review_like.setText(String.valueOf(review.getLike()));
            review_writtenTime.setText(review.getWrittenTime());
        }


        public void setListener() {
            // click more button

            review_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018. 9. 4. 몇 가지 기능 추가로..
                    Toast.makeText(itemView.getContext().getApplicationContext(), "더보기 기능 추가하기.", Toast.LENGTH_SHORT).show();
                }
            });

            // click review contents
            review_screen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭 시 -> 리뷰 페이지로 이동
                    Intent intent = new Intent(v.getContext(), ReviewShow.class);

                    //디버그용.
                    intent.putExtra("uid", review_uid.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });

            // click review like button
            review_likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2018. 9. 8. 버튼 누를 시에 좋아요 색깔 변동
                    long likes = Long.valueOf(review_like.getText().toString());
                    if(like){ // 좋아하는 것에서 -> 취소로
                        likes--;
                        like = false;

                        //디버그 용
                        Toast.makeText(v.getContext().getApplicationContext(),"두번은 못누른다.",Toast.LENGTH_SHORT).show();
                    }else{    // 좋아하기 누름.
                        likes++;
                        like = true;

                        // 디버그 용
                        Toast.makeText(v.getContext().getApplicationContext(), "좋아요!.", Toast.LENGTH_SHORT).show();
                    }

                    review_like.setText(likes+"");
                    myRef.child("reviews").child(review_uid.getText().toString()).child("like").setValue(likes);
                }
            });
        }
    }
}

package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;

import java.util.HashMap;
import java.util.Map;

/*
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
*/

public class ReviewFormActivity extends AppCompatActivity{
    private Button reviewWriteBtn;
    private RatingBar rating;
    private TextView ratingNum;
    private EditText review;

    //db
    private Map<String, Object> user = new HashMap<>();
    private Map<String, Object> data = new HashMap<>();
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        // 폼 작성 액티비티.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_form);

        //data structure
        review = findViewById(R.id.review);
        rating = findViewById(R.id.ratingBar);
        ratingNum = findViewById(R.id.ratingNum);
        reviewWriteBtn = findViewById(R.id.reviewWriteBtn);

        //평점 변경.

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingNum.setText(String.valueOf(ratingBar.getRating()));
            }
        });


        reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 눌렸을 시.
                // TODO: 2018. 8. 30. 디비에 연결 및 값 받아오기
                /*
                db.collection("reviews").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        data.put("contents",review);
                        data.put("id",db.collection("users").getId());
                        db.collection("reviews").document("first").set(data);

                        // 성공 메세지
                        Log.d("성공","리뷰 작성을 성공 하셨습니다." + documentReference.getId());
                        Toast.makeText(getApplicationContext(),"성공했당?",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("실패","리뷰 작성을 실패 하셨습니다.",e);
                        Toast.makeText(getApplicationContext(),"실패했넹?",Toast.LENGTH_SHORT).show();
                    }
                });
                //
                */

                // 평점 입력 안할 시
               if(review.getText().equals("")) {
                   Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                   // TODO: 2018. 8. 30. 알림창으로 변경
               }
                else{
                   // TODO: 2018. 8. 30. 리뷰 작성한 것을 보여주는 페이지로 이동.
                    Intent intent = new Intent(ReviewFormActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }
}

package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
    private RatingBar ratingBar;
    private EditText review;

    //db
    private Map<String, Object> user = new HashMap<>();
    private Map<String, Object> data = new HashMap<>();
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_form);
        // db의 값 가져오기


        review = findViewById(R.id.review);
        ratingBar = findViewById(R.id.ratingBar);
        reviewWriteBtn = findViewById(R.id.reviewWriteBtn);


        reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018. 8. 18. 디비에 값 전달.
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

                // 버튼 성공 시
                Toast.makeText(getApplicationContext(),"됐다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewFormActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}

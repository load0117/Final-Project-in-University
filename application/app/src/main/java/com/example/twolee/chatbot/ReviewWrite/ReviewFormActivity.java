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

public class ReviewFormActivity extends AppCompatActivity{
    private Button reviewWriteBtn;
    private RatingBar ratingBar;
    private EditText review;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_form);

        review = findViewById(R.id.review);
        ratingBar = findViewById(R.id.ratingBar);
        reviewWriteBtn = findViewById(R.id.reviewWriteBtn);

        reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018. 8. 18. 디비에 값 전달.

                //
                Toast.makeText(getApplicationContext(),"됐다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReviewFormActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}

package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewFormActivity extends AppCompatActivity{
    // xml
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile) ImageView profile;
    @BindView(R.id.idShow) TextView idShow;
    @BindView(R.id.reviewWriteScreen) EditText reviewWriteScreen;
    @BindView(R.id.reviewRatingBar) RatingBar reviewRatingBar;
    @BindView(R.id.reviewRatingNum) TextView reviewRatingNum;
    @BindView(R.id.reviewWriteBtn) Button reviewWriteBtn;

    // DataBase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myref = database.getReference();

    // session
    @NonNull
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    // To relationship
    private Map<String, Object> user = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_form);
        ButterKnife.bind(this);

        setToolbar();           //  setting toolbar
        getData();              //  getting data
        setListeners();         //  setting listener
    }

    public void setToolbar(){
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReviewFormActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void getData(){

        myref.child("users").child(uid).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                // profile
                idShow.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("failure",databaseError.toException());
            }
        });
    }

    public void setListeners(){
        // rating bar
        reviewRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // TODO: 2018. 8. 31. 실시간으로 텍스트가 변하는 ratingbar 생각
                reviewRatingNum.setText(String.valueOf(ratingBar.getRating()));
            }
        });

        // review write button click
        reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 리뷰 버튼 눌렀을 시. 해야 할 일. -> 리뷰 자체를 디비에 연결.
                DatabaseReference keyRef = myref.child("reviews").push();

                String rating = String.format(Locale.KOREA,"%.1f",reviewRatingBar.getRating());
                Review review = new Review(uid, idShow.getText().toString(), reviewWriteScreen.getText().toString(), rating, getCurrentTime(),0);
                // TODO: 2018. 8. 31. 현재 시간이 틀리다. 나라 수정으로 현재 시간 바꾸자.

                keyRef.setValue(review);
                user.put(keyRef.getKey(), "/reviews/"+ keyRef.getKey());

                // relationship
                myref.child("users").child(uid).child("reviews").updateChildren(user);

                // not input rating
               if(reviewWriteScreen.getText().toString().equals("")) {
                   Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                   // TODO: 2018. 8. 30. 알림창으로 변경
               }
                else{
                   Toast.makeText(getApplicationContext(),"입력 되었습니다.", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(ReviewFormActivity.this, ReviewShow.class);
                   // TODO: 2018. 9. 4. 뒤로 가기 시에 종료가 되도록 스택에 쌓여 있는 액티비티들 제거 하는 플래그 추가.
                   //intent.putExtra("uid",uid);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                   //차 후 주석 제거.
                   //finish();
                }
            }
        });
    }

    public String getCurrentTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        System.out.println(SimpleDateFormat.getInstance());

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss",Locale.KOREAN);

        return sdfNow.format(date);
    }
}

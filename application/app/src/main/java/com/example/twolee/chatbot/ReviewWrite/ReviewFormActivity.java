package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuthException;
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

/*
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
*/

public class ReviewFormActivity extends AppCompatActivity{
    //터치 가능
    private ImageView profile;
    private TextView id;
    private EditText reviewWriteScreen;
    private Button reviewWriteBtn;
    private RatingBar reviewRatingBar;
    // 터치 불가능
    private TextView reviewRatingNum;
    // 보이지 않음.
    private String receivedId = "load0117";
    private String randomNum;
    //db
    private Map<String, Object> user = new HashMap<>();
    private Map<String, Object> data = new HashMap<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 폼 작성 액티비티.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_form);

        //data structure
        profile = findViewById(R.id.profile);
        id = findViewById(R.id.id);
        reviewWriteScreen = findViewById(R.id.reviewWriteScreen);
        reviewRatingBar = findViewById(R.id.reviewRatingBar);
        reviewRatingNum = findViewById(R.id.reviewRatingNum);
        reviewWriteBtn = findViewById(R.id.reviewWriteBtn);
        // 보이지 않음.

        getDatas();
        // 리스너 설정하기.
        setListeners();
    }

    public void getDatas(){
        // 디비에서 데이터 얻어오기.

        //프로필.
        myref.child(receivedId).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //프로필..
        id.setText(receivedId);
    }

    public void setListeners(){
        reviewRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // TODO: 2018. 8. 31. 실시간으로 텍스트가 변하는 ratingbar 생각
                reviewRatingNum.setText(String.valueOf(ratingBar.getRating()));
            }
        });


        reviewWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference keyRef = myref.child("reviews").push();
                String key = keyRef.getKey();
                setData();
                //reviews 에 넣기
                keyRef.setValue(data);
                //user.put(key, data);
                user.put(key, "/reviews/"+key);
                //사용자 아이디에 리뷰에 연결.
                myref.child("users").child(receivedId).child("reviews").updateChildren(user);


                // 평점 입력 안할 시
               if(reviewWriteScreen.getText().toString().equals("")) {
                   Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_SHORT).show();
                   // TODO: 2018. 8. 30. 알림창으로 변경
               }
                else{
                   // TODO: 2018. 8. 30. 리뷰 작성한 것을 보여주는 페이지로 이동.
                   Toast.makeText(getApplicationContext(),"입력 되었습니다.", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(ReviewFormActivity.this, ReviewShow.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);
                }
            }
        });
    }

    public void setData(){
        //set data
        // session 연결된 아이디 가져오기.
        data.put("id",receivedId);
        data.put("contents",reviewWriteScreen.getText().toString());
        data.put("writtenTime",getCurrentTime());
        // TODO: 2018. 8. 31. 현재 시간이 틀리다. 나라 수정으로 현재 시간 바꾸자.
        String rating = String.format("%.1f",reviewRatingBar.getRating());
        data.put("rating",rating);
    }

    public String getCurrentTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        System.out.println(SimpleDateFormat.getInstance());

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return sdfNow.format(date);
    }
}

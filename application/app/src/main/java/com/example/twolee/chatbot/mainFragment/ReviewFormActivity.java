package com.example.twolee.chatbot.mainFragment;

import android.content.Intent;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.mypageFragment.StateUpdateActivity;
import com.example.twolee.chatbot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

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
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();
    // session
    @NonNull private String userUid = FirebaseAuth.getInstance().getUid();

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
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getData(){

        //profile
        String filename = userUid + ".jpg";
        storage.child("profile").child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //profile_icon.setImageURI(uri);
                Glide.with(getApplicationContext()).load(uri).into(profile);
                Log.w("success","성공");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("fail","실패");
                e.printStackTrace();
            }
        });

        //email
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        idShow.setText(email);
    }

    public void setListeners(){
        // rating bar
        reviewRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                reviewRatingNum.setText(String.valueOf(ratingBar.getRating()));
            }
        });
    }

    @OnClick(R.id.reviewWriteBtn)
    public void writeReview(){
        // 리뷰 버튼 눌렀을 시. 해야 할 일. -> 리뷰 자체를 디비에 연결.
        DatabaseReference keyRef = myRef.child("reviews").push();

        String rating = String.format(Locale.KOREA,"%.1f",reviewRatingBar.getRating());
        Review review = new Review(userUid, idShow.getText().toString(), reviewWriteScreen.getText().toString(), rating, getCurrentTime(),0);

        keyRef.setValue(review);
        user.put(keyRef.getKey(), "/reviews/"+ keyRef.getKey());

        // relationship
        myRef.child("users").child(userUid).child("reviews").updateChildren(user);

        // not input rating
        if(reviewWriteScreen.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "내용을 입력하세요.", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(getApplicationContext(),"입력 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ReviewFormActivity.this, MainActivity.class);
            HomeFragment.newInstance();
            startActivity(intent);
            finish();
        }
    }

    public String getCurrentTime(){
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        System.out.println(SimpleDateFormat.getInstance());

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss",Locale.KOREAN);

        return sdfNow.format(date);
    }
}


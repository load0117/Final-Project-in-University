package com.example.twolee.chatbot.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.twolee.chatbot.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileShowActivity extends AppCompatActivity {

    @BindView(R.id.review_back_button)
    ImageButton backButton;
    @BindView(R.id.review_profile_show)
    ImageView profileShow;

    private StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_profile_show);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        String userUid = intent.getStringExtra("userUid");

        setProfile(userUid);
    }

    public void setProfile(String userUid){
        storage.child("profile").child(userUid).getDownloadUrl()
                .addOnSuccessListener( (uri) -> Glide.with(getApplicationContext()).load(uri).into(profileShow))
                .addOnFailureListener((e) -> Log.w("fail","불러오기 실패"));
    }

    @OnClick(R.id.review_back_button)
    public void backButtonPressed(){ finish(); }
}

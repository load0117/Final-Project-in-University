package com.example.twolee.chatbot.mypageFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.twolee.chatbot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoShowActivity extends AppCompatActivity{

    @BindView(R.id.profile_detail_show)
    ImageView profile_detail_show;
    @BindView(R.id.back_button)
    ImageButton back_button;

    PhotoViewAttacher photoViewAttacher;

    StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_photo_show);
        ButterKnife.bind(this);

        //profile_detail_show
        String filename = FirebaseAuth.getInstance().getUid()+".jpg";
        storage.child("profile").child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //profile_icon.setImageURI(uri);
                Glide.with(getApplicationContext()).load(uri).into(profile_detail_show);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Toast.makeText(getApplicationContext(), "파일을 불러 올 수 없습니다.", Toast.LENGTH_SHORT).show();
                Log.w("fail","불러오기 실패");
            }
        });

        // 캡쳐 여부
        profile_detail_show.setDrawingCacheEnabled(false);

        photoViewAttacher = new PhotoViewAttacher(profile_detail_show);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}

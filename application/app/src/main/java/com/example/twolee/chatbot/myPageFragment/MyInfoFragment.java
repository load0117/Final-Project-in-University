package com.example.twolee.chatbot.myPageFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static android.app.Activity.RESULT_OK;

public class MyInfoFragment extends Fragment {

    // no exist current user
    @Nullable @BindView(R.id.require_id_button) Button require_id_button;
    // exist current user
    @Nullable @BindView(R.id.profile_icon) ImageView profile_icon;
    @Nullable @BindView(R.id.userEmail) TextView user_email;
    @Nullable @BindView(R.id.profile_state) TextView profile_state;
    @Nullable @BindView(R.id.profile_changeBtn) Button profile_changeBtn;
    @Nullable @BindView(R.id.state_updateBtn) Button state_updateBtn;
    @Nullable @BindView(R.id.logout_button) Button logout_button;

    // dataBase
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    // profile photo select
    private static final int FROM_ALBUM = 1;

    // is exist login user
    private boolean isExistUser = false;

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v;
        if (firebaseAuth.getCurrentUser() == null) {
            v = inflater.inflate(R.layout.fragment_item_noinfo, container, false);
            isExistUser = false;
        } else {
            v = inflater.inflate(R.layout.fragment_item_myinfo, container, false);
            isExistUser = true;
        }

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Run after activity onCreate method
        super.onActivityCreated(savedInstanceState);

        if (isExistUser)
            setData();
    }

    /* ---  no login  --- */
    @Optional
    @OnClick(R.id.require_id_button)
    public void require(View v){
        Intent intent = new Intent(v.getContext(), LoginActivity.class);
        startActivity(intent);

        if(getActivity() != null)
            getActivity().finishAffinity();
    }

    /* ---  if Login  --- */
    public void setData() throws NullPointerException {
        try {

            // getting profile icon
            @NonNull String filename = "";
            if(firebaseAuth.getUid() != null)
                filename = firebaseAuth.getUid();

            storage.child("profile").child(filename).getDownloadUrl()
                    .addOnSuccessListener((uri) -> {
                        if(getActivity() != null){
                            // Glide 를 이용해서 파일 이미지 저장
                            if(profile_icon != null)
                                Glide.with(getActivity()).load(uri).into(profile_icon);

                            Log.w("success","이미지 불러오기 성공");
                        }
                    })
                    .addOnFailureListener((e)-> Log.w("fail","이미지 불러오기 실패, 불러온 이미지 없음"));

            String userUid = firebaseAuth.getUid();

            // user email
            if(user_email != null && firebaseAuth.getCurrentUser() != null)
                user_email.setText(firebaseAuth.getCurrentUser().getEmail());

            // getting state
            myRef.child("users").child(userUid).child("state").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String stateMessage = dataSnapshot.getValue(String.class);
                    if (stateMessage == null)
                        Log.w("nullPointException", "널 포인트");
                    else if (stateMessage.length() == 0 && profile_state != null)
                        profile_state.setHint("상태를 적어 주세요");
                    else if (profile_state != null)
                        profile_state.setText(stateMessage);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("failure", "디비 연동 실패");
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    /* button listeners */

    @Optional
    @OnClick(R.id.profile_icon)
    public void icon(){
        Intent intent = new Intent(getActivity(), PhotoShowActivity.class);
        startActivity(intent);
    }

    @Optional
    @OnClick(R.id.state_updateBtn)
    public void updateState(){
        Intent intent = new Intent(getActivity(), StateUpdateActivity.class);
        startActivity(intent);
    }

    @Optional
    @OnClick(R.id.profile_changeBtn)
    public void changeProfile(View v){
        makeDialog(v);
    }

    @Optional
    @OnClick(R.id.logout_button)
    public void logout(View v){

        if (firebaseAuth != null)
            firebaseAuth.signOut();

        isExistUser = false;
        Intent intent = new Intent(v.getContext(), LoginActivity.class);
        startActivity(intent);
        if(getActivity() != null)
            getActivity().finish();
    }

    /* ---  help functions  --- */
    private void makeDialog(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        dialog.setTitle("업로드").setIcon(R.drawable.ic_camera_uploading).setCancelable(false)
                .setPositiveButton("앨범 선택",(dialogInterface, which) -> selectAlbum())
                .setNegativeButton("취소", (dialogInterface, which) -> dialogInterface.cancel());

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void selectAlbum() {
        // 앨범에서 파일 선택.
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == FROM_ALBUM){
            if(resultCode == RESULT_OK){
                try{
                    InputStream in = null;

                    if(getActivity() != null && data.getData() != null){
                        in = getActivity().getContentResolver().openInputStream(data.getData());
                    }

                    Bitmap img = BitmapFactory.decodeStream(in);

                    // Database

                    if(data.getData() != null && firebaseAuth != null && firebaseAuth.getUid() != null)
                        storage.child("profile").child(firebaseAuth.getUid()).putFile(data.getData());

                    if(profile_icon != null)
                        profile_icon.setImageBitmap(img);

                    if( in != null)
                        in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.example.twolee.chatbot.mypageFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.login.LoginActivity;
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

import java.io.File;
import java.io.IOException;
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
    @Nullable @BindView(R.id.profile_state) TextView profile_state;
    @Nullable @BindView(R.id.profile_changeBtn) Button profile_changeBtn;
    @Nullable @BindView(R.id.state_updateBtn) Button state_updateBtn;
    @Nullable @BindView(R.id.logout_button) Button logout_button;

    // dataBase
    @Nullable FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();

    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    // profile photo select
    private String mCurrentPhotoPath;
    private Uri imgUri, photoURI, albumURI;

    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;

    // is exist login user
    private boolean isExistUser = false;

    public static MyInfoFragment newInstance() {
        MyInfoFragment myInfoFragment = new MyInfoFragment();
        return myInfoFragment;
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

    @Optional
    @OnClick(R.id.require_id_button)
    public void require(View v){
        Intent intent = new Intent(v.getContext(), LoginActivity.class);
        startActivity(intent);

        if(getActivity() != null)
            getActivity().finishAffinity();
    }

    public void setData() throws NullPointerException {
        try {
            String filename = firebaseAuth.getUid();
            storage.child("profile").child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(getActivity() != null)
                        Glide.with(getActivity()).load(uri).into(profile_icon);
                    Log.w("success","성공");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("fail","실패");
                }
            });

            String userUid = firebaseAuth.getUid();

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
                    Log.w("failure", "실패");
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

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

    private void makeDialog(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
        dialog.setTitle("업로드").setIcon(R.drawable.ic_camera_uploading).setCancelable(false)
                .setPositiveButton("앨범 선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectAlbum();
                    }
                })
                .setNeutralButton("사진 촬영", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takePhoto();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void takePhoto() {
        // 촬영 후 이미지 가져옴 - 현재 수정중
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    Uri providerURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName(), photoFile);
                    imgUri = providerURI;
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }
        } else
            Log.v("알림", "저장공간에 접근 불가능");
    }

    private void selectAlbum() {
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
                    storage.child("profile").child(firebaseAuth.getUid()).putFile(data.getData());

                    if(profile_icon != null)
                        profile_icon.setImageBitmap(img);


                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    // 카메라로 찍은 이미지 생성
    public File createImageFile() throws IOException {
        String imgFileName = System.currentTimeMillis() + ".jpg";
        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }

        Log.v("알림", "storageDir 존재함 " + storageDir.toString());
        imageFile = new File(storageDir, imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        return imageFile;
    }

    // 촬영한 이미지 저장하기
    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
        //Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
        // TODO: 17/10/2018 크기 변형해서 저장하기
    }

}

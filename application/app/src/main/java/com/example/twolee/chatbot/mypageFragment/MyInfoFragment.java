package com.example.twolee.chatbot.mypageFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
    @Nullable @BindView(R.id.userEmail) TextView user_email;
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
            String filename = firebaseAuth.getUid();
            storage.child("profile").child(filename).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    if(getActivity() != null){
                        // Glide 를 이용해서 파일 이미지 저장
                        Glide.with(getActivity()).load(uri).into(profile_icon);
                    }

                    Log.w("success","이미지 불러오기 성공");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("fail","이미지 불러오기 실패");
                }
            });

            String userUid = firebaseAuth.getUid();

            // user email
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
        //sd카드 정상적으로 작동하는지 여부 알아보기 위해서.
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            /* SD카드가 존재하고 연결이 되었으며 읽고 쓰기가 가능한 상태 */

            // 화면 캡처
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                /*수신할 앱이 있으면*/
                File photoFile = null;

                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    //Uri providerURI = FileProvider.getUriForFile(getContext(), getContext().getPackageName(), photoFile);
                    //imgUri = providerURI;
                    //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);

                    // 카메라 실행
                    startActivityForResult(intent, FROM_CAMERA);
                }
            }

        } else
            Log.v("알림", "저장공간에 접근 불가능");
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

                    if(data.getData() != null)
                        storage.child("profile").child(firebaseAuth.getUid()).putFile(data.getData());

                    if(profile_icon != null)
                        profile_icon.setImageBitmap(img);


                    in.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else if (requestCode == FROM_CAMERA) {
            if(resultCode == RESULT_OK) {
                galleryAddPic();
                Log.w("ms", "미디어 스캔까지 완료");
                if(profile_icon != null)
                    profile_icon.setImageURI(data.getData());

                if(data.getData() != null)
                    storage.child("profile").child(firebaseAuth.getUid()).putFile(data.getData());
                Log.w("ms","이미지 뷰에 저장까지 완료");
            }
        }
    }

    // 카메라 앱으로 넘어가서 사진 찍는 메서드
    public File createImageFile() throws IOException {
        // TODO: 24/10/2018 파일 경로 확인 및 카메라 연동 완성
        String imgFileName = System.currentTimeMillis() + ".jpg";

        File imageFile = null;
        System.out.println("camera path :"+ Environment.getExternalStorageDirectory()+"/Pictures");
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/dcim/","Camera");

        if (!storageDir.exists()) {
            Log.v("알림", "storageDir 존재 x " + storageDir.toString());
            storageDir.mkdirs();
        }else{
            Log.v("알림", "storageDir 존재함 " + storageDir.toString());
        }

        imageFile = new File(storageDir, imgFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        System.out.println("파일 경로:"+mCurrentPhotoPath);
        return imageFile;
    }

    // 촬영한 이미지를 갤러리에 저장하는 메서드 - 미디어 스캔
    public void galleryAddPic() {
        /*

            미디어 스캔

            사진을 찍은 후에 갤러리에 바로 보이지 않는 경우는 안드로이드의 미디어 라이러리에 파일이 추가 되지 않아서 생기는 현상이다.

            그럴 때는 보통 sd 카드를 다시 장착하는, 즉 미디어 스캔을 해줘야 하는데, 매번 그럴 수는 없으니 수동으로 미디어 스캔을 해줘야 한다. 그 방법은 다음과 같다.

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://"+Environment.getExternalStorageDirectory())));

            하지만 이 방법은 성능 저하를 일으키므로 kitkat버전 이후에는 사용할 수 없고 그 이후 버전에 사용할 수 있는 방법은 다음과 같다.

         */

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);

        Log.w("sucess", "사진 저장 성공");
    }

}

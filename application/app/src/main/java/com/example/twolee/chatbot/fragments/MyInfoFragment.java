package com.example.twolee.chatbot.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInfoFragment extends Fragment {

    @Nullable @BindView(R.id.profile_icon) ImageView profile_icon;
    @Nullable @BindView(R.id.profile_state) TextView profile_state;
    @Nullable @BindView(R.id.profile_changeBtn) Button profile_changeBtn;
    @Nullable @BindView(R.id.state_updateBtn) Button state_updateBtn;
    @Nullable @BindView(R.id.require_id_button) Button require_id_button;

    // dataBase
    FirebaseUser currentUser;
    StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();

    // profile photo select

    // is exist login user
    private boolean isExistUser = false;

    public static MyInfoFragment newInstance(){
        MyInfoFragment myInfoFragment = new MyInfoFragment();
        return myInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 변수 할당.
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null) {
            v = inflater.inflate(R.layout.fragment_item_noinfo, container, false);
            isExistUser = false;
        }else {
            v = inflater.inflate(R.layout.fragment_item_myinfo, container, false);
            isExistUser = true;
        }

        ButterKnife.bind(this,v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Run after activity onCreate method
        super.onActivityCreated(savedInstanceState);

        if(isExistUser){

            state_updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 상태 메시지 입력 - 입력 창 생성 하기
                    Toast.makeText(getActivity(), "상태 변경 클릭", Toast.LENGTH_SHORT).show();
                }
            });

            profile_changeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //profile photo 버튼 추가
                    Toast.makeText(getActivity(), "프로필 변경 클릭", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            require_id_button = getActivity().findViewById(R.id.require_id_button);

            // 로그인 필요 버튼
            require_id_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =  new Intent(v.getContext(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }

    }
}

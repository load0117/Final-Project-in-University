package com.example.twolee.chatbot.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.twolee.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyInfoFragment extends Fragment {
    @BindView(R.id.profile_icon)
    ImageView profile_icon;
    @BindView(R.id.profile_changeBtn)
    ImageButton profile_changeBtn;
    @BindView(R.id.state_updateBtn)
    Button state_updateBtn;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();

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
        if(currentUser != null)
            v = inflater.inflate(R.layout.fragment_item_noinfo, container,false);
        else
            v = inflater.inflate(R.layout.fragment_item_myinfo, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        // Run after activity onCreate method

        super.onActivityCreated(savedInstanceState);

        state_updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setState();
            }
        });

        profile_changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //프로필 변경
                setProfileIcon();
            }
        });

    }

    public void setState(){

    }

    public void setProfileIcon(){

    }

}

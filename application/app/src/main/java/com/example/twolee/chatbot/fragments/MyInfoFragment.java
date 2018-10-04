package com.example.twolee.chatbot.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyInfoFragment extends Fragment {
    //@BindView(R.id.profile_icon) ImageView profile_icon;
    //@BindView(R.id.profile_state) TextView profile_state;
    //@BindView(R.id.profile_changeBtn) Button profile_changeBtn;
    //@BindView(R.id.state_updateBtn) Button state_updateBtn;
    //@BindView(R.id.require_id_button) Button require_id_button;
    ImageView profile_icon;
    TextView profile_state;
    Button profile_changeBtn;
    Button state_updateBtn;
    Button require_id_button;
    //private Button require_id_button;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference storage = FirebaseStorage.getInstance("gs://chatbot-6c425.appspot.com").getReference();
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
        if(currentUser == null) {
            v = inflater.inflate(R.layout.fragment_item_noinfo, container, false);
            isExistUser = false;
        }else {
            v = inflater.inflate(R.layout.fragment_item_myinfo, container, false);
            isExistUser = true;
        }

        //ButterKnife.bind(this,v);

        //ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Run after activity onCreate method
        Toast.makeText(getContext().getApplicationContext(), "그래가~", Toast.LENGTH_SHORT).show();
        super.onActivityCreated(savedInstanceState);

        if(isExistUser){
            setData();
            state_updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "상태 변경 클릭", Toast.LENGTH_SHORT).show();
                }
            });

            profile_changeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "프로필 변경 클릭", Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            require_id_button = getActivity().findViewById(R.id.require_id_button);

            require_id_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"로그인 필요", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    public void setData() throws NullPointerException{
        profile_state = getActivity().findViewById(R.id.profile_state);
        state_updateBtn = getActivity().findViewById(R.id.state_updateBtn);
        profile_changeBtn = getActivity().findViewById(R.id.profile_changeBtn);
    }

    public void setState(){

    }

    public void setProfileIcon(){

    }

}

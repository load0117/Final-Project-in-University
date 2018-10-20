package com.example.twolee.chatbot.noteFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteFragment extends Fragment {
    @Nullable @BindView(R.id.homework_recyclerView)
    RecyclerView homework_recyclerView;
    @Nullable @BindView(R.id.noHomeworkShow)
    TextView noHomeworkShow;

    @Nullable @BindView(R.id.require_id_button)
    Button require_id_button;


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private HomeworkAdapter homeworkAdapter;
    private boolean isExistUser = false;

    public static NoteFragment newInstance() {
        NoteFragment fragment = new NoteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v;

        if(firebaseAuth.getCurrentUser() != null){
            v = inflater.inflate(R.layout.fragment_item_note, container, false);
            isExistUser = true;
        }else {
            v = inflater.inflate(R.layout.fragment_item_noinfo, container, false);
            isExistUser = false;
        }

        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        try{
            if (isExistUser) {

                HomeworkReader.getInitData(new HomeworkListener() {
                    @Override
                    public void goalListener(List<Homework> homeworkList, List<String> homeworkUidList) {

                        homeworkAdapter = new HomeworkAdapter(homeworkList, homeworkUidList);
                        homework_recyclerView.setAdapter(homeworkAdapter);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setReverseLayout(true);
                        linearLayoutManager.setStackFromEnd(true);

                        homework_recyclerView.setLayoutManager(linearLayoutManager);

                        try{
                            if(homeworkList.size()==0){
                                homework_recyclerView.setVisibility(View.INVISIBLE);
                                noHomeworkShow.setVisibility(View.VISIBLE);
                            }else{
                                noHomeworkShow.setVisibility(View.INVISIBLE);
                                homework_recyclerView.setVisibility(View.VISIBLE);
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                });

            }else{
                // 로그인 필요 버튼
                require_id_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

}
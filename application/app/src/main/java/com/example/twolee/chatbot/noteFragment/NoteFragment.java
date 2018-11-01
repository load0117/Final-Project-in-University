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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class NoteFragment extends Fragment {
    @Nullable
    @BindView(R.id.homework_recyclerView)
    RecyclerView homework_recyclerView;
    @Nullable
    @BindView(R.id.noHomeworkShow)
    TextView noHomeworkShow;

    @Nullable
    @BindView(R.id.require_id_button)
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

        if (firebaseAuth.getCurrentUser() != null) {
            v = inflater.inflate(R.layout.fragment_item_note, container, false);
            isExistUser = true;
        } else {
            v = inflater.inflate(R.layout.fragment_item_noinfo, container, false);
            isExistUser = false;
        }

        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HomeworkReader homeworkReader = new HomeworkReader();
        if (isExistUser) {
            homeworkReader.getInitData((homeworkList, homeworkUidList) -> {

                if (homework_recyclerView != null) {

                    homeworkAdapter = new HomeworkAdapter(homeworkList, homeworkUidList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setReverseLayout(true);
                    linearLayoutManager.setStackFromEnd(true);
                    homework_recyclerView.setLayoutManager(linearLayoutManager);
                    homework_recyclerView.setAdapter(homeworkAdapter);

                    if (homeworkList.size() == 0 && noHomeworkShow != null) {
                        // 비어 있는데 로그인은 되어 있으면?
                        noHomeworkShow.setVisibility(View.VISIBLE);
                        homework_recyclerView.setVisibility(View.INVISIBLE);
                    } else if (noHomeworkShow != null) {
                        // 비어 있지 않으면?
                        noHomeworkShow.setVisibility(View.INVISIBLE);
                        homework_recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    @Optional
    @OnClick(R.id.require_id_button)
    public void require(View v) {
        System.out.println(v);
        System.out.println("밖에서 돌린 컨택스트:" + this.getView().getContext());
        Intent intent = new Intent(this.getView().getContext(), LoginActivity.class);
        startActivity(intent);
        if (getActivity() != null)
            getActivity().finish();
    }
}
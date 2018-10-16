package com.example.twolee.chatbot.noteFragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.psycological.HomeworkAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteFragment extends Fragment {
    @BindView(R.id.homework_recyclerView)
    RecyclerView homework_recyclerView;

    private HomeworkAdapter homeworkAdapter;

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
        View v = inflater.inflate(R.layout.fragment_item_note, container, false);

        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        homeworkAdapter = new HomeworkAdapter();

        homework_recyclerView.setAdapter(homeworkAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        homework_recyclerView.setLayoutManager(linearLayoutManager);


    }

}
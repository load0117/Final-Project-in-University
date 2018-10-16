package com.example.twolee.chatbot.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.example.twolee.chatbot.noteFragment.NoteFragment;
import com.example.twolee.chatbot.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    // 뷰 level
    @BindView(R.id.startBtn)
    Button startBtn;
    @BindView(R.id.writeBtn)
    Button writeBtn;
    @BindView(R.id.noteButton)
    Button noteButton;

    @BindView(R.id.home_recycler_view) RecyclerView home_recycler_view;

    // 코드 level
    private ReviewAdapter reviewAdapter;

    private Fragment selectedFragment;
    private FragmentTransaction transaction;

    public static HomeFragment newInstance() {
        HomeFragment  homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 변수 할당.
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // create view
        View v = inflater.inflate(R.layout.fragment_item_home, container, false);

        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Run after activity onCreate method
        super.onActivityCreated(savedInstanceState);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    // Using Sign In
                    Intent intent = new Intent(getActivity(), ReviewFormActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    //  Not Sign In + Sign Up
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        // 버튼 불 들어오도록..
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = NoteFragment.newInstance();
                transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,selectedFragment);
                transaction.commit();
            }
        });

        ReviewLoader.getInitData(new ReviewListener() {
            @Override
            public void runListener(List<Review> reviewArrayList, List<String> review_uidList) {

                reviewAdapter = new ReviewAdapter(reviewArrayList, review_uidList);
                home_recycler_view.setAdapter(reviewAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);

                home_recycler_view.setLayoutManager(linearLayoutManager);
            }
        });

    }
}

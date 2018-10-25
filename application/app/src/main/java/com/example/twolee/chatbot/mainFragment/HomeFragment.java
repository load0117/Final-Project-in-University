package com.example.twolee.chatbot.mainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.example.twolee.chatbot.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {
    // 뷰
    @BindView(R.id.startBtn)
    TextView startBtn;
    @BindView(R.id.writeBtn)
    Button writeBtn;

    @BindView(R.id.home_recycler_view) RecyclerView home_recycler_view;

    // 코드
    private ReviewAdapter reviewAdapter;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
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
    // 확인 버튼 클릭시 이벤트
    @OnClick(R.id.startBtn)
    public void start(){
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        startActivity(intent);
    }

    // 리뷰 작성시 이벤트
    @OnClick(R.id.writeBtn)
    public void write(){
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

}

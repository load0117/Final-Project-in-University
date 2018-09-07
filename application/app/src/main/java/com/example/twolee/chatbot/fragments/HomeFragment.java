package com.example.twolee.chatbot.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.ReviewWrite.ReviewListener;
import com.example.twolee.chatbot.ReviewWrite.Review;
import com.example.twolee.chatbot.ReviewWrite.ReviewAdapter;
import com.example.twolee.chatbot.ReviewWrite.ReviewFormActivity;
import com.example.twolee.chatbot.ReviewWrite.ReviewLoader;
import com.example.twolee.chatbot.chatting.ChatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
//import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {
    // 뷰 level
    @BindView(R.id.startBtn) Button startBtn;
    @BindView(R.id.writeBtn) Button writeBtn;
    private RecyclerView home_recycle_view;
    //private RecyclerView home_recycle_view;
    // TODO: 2018. 8. 30. 상단 고정 시키기.
    //private Button startBtn;
    //private Button writeBtn;

    // 코드 level
    private ReviewAdapter reviewAdapter;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 변수 할당.
        super.onCreate(savedInstanceState);

    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // create view
        View v = inflater.inflate(R.layout.fragment_item_home,container,false);

        ButterKnife.bind(this,v);
        home_recycle_view = v.findViewById(R.id.home_recycle_view);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        // Run after activity onCreate method
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(home_recycle_view);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewFormActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ReviewLoader.getInitData(new ReviewListener() {
            @Override
            public void runListener(List<Review> reviewArrayList) {

                reviewAdapter = new ReviewAdapter(reviewArrayList);
                //reviewAdapter.notifyDataSetChanged();
                home_recycle_view.setAdapter(reviewAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                home_recycle_view.setLayoutManager(linearLayoutManager);
            }
        });

        // TODO: 2018. 8. 27. 뒤로 가기 하면 메인 화면이 뜰 수 있도록 플래그 수정.
        // TODO: 2018. 8. 30. 롱 터치 시 이벤트 처리
    }
}

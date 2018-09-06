package com.example.twolee.chatbot.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.ReviewWrite.Review;
import com.example.twolee.chatbot.ReviewWrite.ReviewAdapter;
import com.example.twolee.chatbot.ReviewWrite.ReviewFormActivity;
import com.example.twolee.chatbot.ReviewWrite.ReviewLoader;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
//import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {
    // 뷰 level
    private RecyclerView home_recycle_view;
    // TODO: 2018. 8. 30. 상단 고정 시키기.
    private Button startBtn;
    private Button writeBtn;

    // 코드 level
    private List<Review> reviewArrayList;
    private ReviewAdapter reviewAdapter;

    /* Real Time */
    //private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    /* Cloud Fire Store */
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        //생성후 뷰 만들 때
        View v = inflater.inflate(R.layout.fragment_item_home,container,false);

        // 성능 문제..
        startBtn = v.findViewById(R.id.startBtn);
        writeBtn = v.findViewById(R.id.writeBtn);

        home_recycle_view = v.findViewById(R.id.home_recycle_view);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        // activity onCreate 완료 후에 실행

        //리스너 생성.
        super.onActivityCreated(savedInstanceState);
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

        // 1. 데이터 받아오기
        reviewArrayList = ReviewLoader.getInitData();
        // 2. 어댑터
        reviewAdapter = new ReviewAdapter(reviewArrayList);
        // 3. 리사이클러 뷰 어댑터 연결
        home_recycle_view.setAdapter(reviewAdapter);
        // 4. 레이아웃 관리자 생성.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        home_recycle_view.setLayoutManager(linearLayoutManager);

        // TODO: 2018. 8. 27. 뒤로 가기 하면 메인 화면이 뜰 수 있도록 플래그 수정.
        // TODO: 2018. 8. 30. 롱 터치 시 이벤트 처리
    }
}

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
import com.example.twolee.chatbot.chatting.ChatActivity;

import java.util.ArrayList;
//import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {
    // 뷰 level
    private RecyclerView home_recycle_view;
    // TODO: 2018. 8. 30. 상단 고정 시키기.
    private Button startBtn;
    private Button writeBtn;

    // 코드 level
    private ArrayList<Review> reviewArrayList;
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
        //생성 되면서.
        super.onCreate(savedInstanceState);

        reviewArrayList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewArrayList);
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
        //액티비티 실행후.

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

        // 레이아웃 관리자 생성.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        home_recycle_view.setLayoutManager(linearLayoutManager);
        home_recycle_view.setAdapter(reviewAdapter);

        /*
        home_recycle_view.addOnItemTouchListener(new RecyclerTouchListener(getContext(), home_recycle_view, new ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                // TODO: 2018. 8. 27. 뒤로 가기 하면 메인 화면이 뜰 수 있도록 플래그 수정.

                Intent intent = new Intent(getActivity(), ReviewShow.class);
                startActivity(intent);


                Thread thread = new Thread(new Runnable() {
                    public void run() {

                    }
                });
                thread.start();

            }

            @Override
            public void onLongClick(View view, int position) {
                // TODO: 2018. 8. 30. 롱 터치 시 이벤트 처리
                Toast.makeText(getActivity(), "롱 터치", Toast.LENGTH_SHORT).show();
            }
        }));
        */
    }

}

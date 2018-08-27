package com.example.twolee.chatbot.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.ReviewWrite.Review;
import com.example.twolee.chatbot.ReviewWrite.ReviewAdapter;
import com.example.twolee.chatbot.ReviewWrite.ReviewFormActivity;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.example.twolee.chatbot.chatting.ClickListener;
import com.example.twolee.chatbot.chatting.RecyclerTouchListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
//import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {
    // 뷰 level
    private RecyclerView home_recycle_view;
    private Button startBtn;
    private Button writeBtn;

    // 코드 level
    private ArrayList<Review> reviewArrayList;
    private ReviewAdapter reviewAdapter;

    /* Real Time */
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    /* Cloud Fire Store */
    //private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reviewArrayList = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviewArrayList);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_home,container,false);

        startBtn = v.findViewById(R.id.startBtn);
        writeBtn = v.findViewById(R.id.writeBtn);

        home_recycle_view = v.findViewById(R.id.home_recycle_view);

        return v;
    }

    public void createRecyclerView(){
        for(int w=0; w<20; w++){
            Review review = new Review();
            review.setContents("cnt:"+w);
            reviewArrayList.add(review);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        //액티비티 실행후.
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

        createRecyclerView();

        /*
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("change", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("fail", "Failed to read value.", databaseError.toException());
            }
        });
        */
        // TODO: 2018. 8. 18. 리사이클 뷰 내용 불러오기.- 찾아보기
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);

        home_recycle_view.setLayoutManager(linearLayoutManager);
        home_recycle_view.setAdapter(reviewAdapter);


        home_recycle_view.addOnItemTouchListener(new RecyclerTouchListener(getContext(), home_recycle_view, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        Review review;
                        try {
                            // TODO: 2018. 8. 27. 상세 내용으로 넘어가는 액티비티 생성
                            /*
                            review = reviewArrayList.get(position);

                            streamPlayer = new StreamPlayer();
                            if (audioMessage != null && !audioMessage.getMessage().isEmpty())
                                //Change the Voice format and choose from the available choices
                                streamPlayer.playStream(service.synthesize(audioMessage.getMessage(), Voice.EN_ALLISON).execute());
                            else
                                streamPlayer.playStream(service.synthesize("No Text Specified", Voice.EN_LISA).execute());
                            */
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}

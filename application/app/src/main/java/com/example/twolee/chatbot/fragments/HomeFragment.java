package com.example.twolee.chatbot.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.chatting.ChatActivity;


public class HomeFragment extends Fragment {
    private RecyclerView home_recycle_view;
    private Button startBtn;
    private Button writeBtn;

    public static HomeFragment newInstance(){
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragment_item_home xml 실행
        View v = inflater.inflate(R.layout.fragment_item_home,container,false);
        startBtn = v.findViewById(R.id.startBtn);
        home_recycle_view = v.findViewById(R.id.home_recycle_view);
        // TODO: 2018. 8. 18. 리스너 추가하기. 
        return v;
    }
    
}

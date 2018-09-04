package com.example.twolee.chatbot.ReviewWrite;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twolee.chatbot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ReviewShow extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_detail);

    }

    public ReviewShow(){
        //리뷰 보여주는 액티비티
        // 상세한 내용을 보여준다.
        // TODO: 2018. 8. 27. 디비에서 데이터 받아오고

        // TODO: 2018. 8. 27. 데이터 받아온 것 양식에 맞게 보여주기.


    }

}

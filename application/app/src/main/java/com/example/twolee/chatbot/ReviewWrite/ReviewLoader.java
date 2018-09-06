package com.example.twolee.chatbot.ReviewWrite;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewLoader {
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    public static List<Review> reviewArrayList;

    public static List<Review> getInitData(){
        reviewArrayList = new ArrayList<>();
        int limit = 6;

        for(int i=0; i<limit; i++){
            //Review review = new Review("hi","df","asd","1.0","23",1);
            //reviewArrayList.add(review);
        }

        Query query = myRef.child("reviews").orderByChild("writtenTime").limitToLast(limit);


        return reviewArrayList;
    }


}

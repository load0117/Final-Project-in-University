package com.example.twolee.chatbot.ReviewWrite;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewLoader{
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private static List<Review> reviewArrayList;

    public static void getInitData(final ReviewListener reviewListener){
        reviewArrayList = new ArrayList<>();
        int limit = 6;

        Query query = myRef.child("reviews").orderByChild("writtenTime").limitToLast(limit);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Review review = data.getValue(Review.class);
                    Review newReview = new Review(review.getUid(),review.getUsername(),review.getContents(),review.getRating(),review.getWrittenTime(),review.getLike());
                    reviewArrayList.add(newReview);
                }

                reviewListener.runListener(reviewArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("fail",databaseError.toException());
            }
        });
    }
}

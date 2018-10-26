package com.example.twolee.chatbot.mainFragment;

import android.support.annotation.Nullable;
import android.util.Log;

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
    private static List<Review> reviewArrayList;
    private static List<String> reviewUidList;

    public static void getInitData(final ReviewListener reviewListener) {
        reviewArrayList = new ArrayList<>();
        reviewUidList = new ArrayList<>();
        int limit = 6;

        Query query = myRef.child("reviews").orderByChild("writtenTime").limitToLast(limit);

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Review review = data.getValue(Review.class);

                    Review newReview = new Review(review.getUserUid(), review.getUsername(), review.getContents(), review.getRating(), review.getWrittenTime());

                    // 정보 담기.
                    reviewArrayList.add(newReview);
                    reviewUidList.add(data.getKey());
                }

                reviewListener.runListener(reviewArrayList, reviewUidList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("fail", databaseError.toException());
            }
        });
    }
}

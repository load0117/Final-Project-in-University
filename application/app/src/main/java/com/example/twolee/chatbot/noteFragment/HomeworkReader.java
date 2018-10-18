package com.example.twolee.chatbot.noteFragment;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeworkReader {
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private static String userUid = FirebaseAuth.getInstance().getUid();
    private static List<Homework> homeworkList;

    public static void getInitData(final HomeworkListener homeworkListener){
        int limit = 7;
        homeworkList = new ArrayList<>();

        myRef.child("homework").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Homework newHomework = data.getValue(Homework.class);
                    homeworkList.add(newHomework);
                }

                homeworkListener.goalListener(homeworkList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("fail",databaseError.toException());
            }
        });
    }
}

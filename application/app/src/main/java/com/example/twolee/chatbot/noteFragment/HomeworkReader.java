package com.example.twolee.chatbot.noteFragment;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeworkReader {
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private static String userUid = FirebaseAuth.getInstance().getUid();

    private static List<Homework> homeworkList;
    private static List<String> homeworkUidList;

    public static void getInitData(final HomeworkListener homeworkListener){
        homeworkList = new ArrayList<>();
        homeworkUidList = new ArrayList<>();

        myRef.child("homeworks").child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Homework getHomework = data.getValue(Homework.class);
                    Homework newHomework = new Homework(getHomework.getGoal(), getHomework.getStartTime(), getHomework.getEndTime());

                    homeworkList.add(newHomework);
                    homeworkUidList.add(data.getKey());
                }

                homeworkListener.goalListener(homeworkList, homeworkUidList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("fail",databaseError.toException());
            }
        });
    }
}

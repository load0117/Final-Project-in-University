package com.example.twolee.chatbot.noteFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckingHomework {
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("homeworks");

    public static CheckingHomework getInstance(){
        CheckingHomework checkingHomework = new CheckingHomework();
        return checkingHomework;
    }

    public static void setChecking(String homeworkUid, Boolean bool){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String userUid = FirebaseAuth.getInstance().getUid();
            myRef.child(userUid).child(homeworkUid).child("isChecked").setValue(bool);
        }
    }
}

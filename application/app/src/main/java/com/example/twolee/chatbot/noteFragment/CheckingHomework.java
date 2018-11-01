package com.example.twolee.chatbot.noteFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckingHomework {
    private String userUid = FirebaseAuth.getInstance().getUid();
    private  DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/" + userUid);

    // 체크 박스 누를 시에 과제 내의 isChecked 를 변화 시킴
    public void setChecking(String homeworkUid, Boolean bool) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String userUid = FirebaseAuth.getInstance().getUid();
            if (userUid != null && !userUid.equals(""))
                myRef.child("tasks").child(homeworkUid).child("checked").setValue(bool);
        } else {
            System.out.println("error");
        }
    }
}

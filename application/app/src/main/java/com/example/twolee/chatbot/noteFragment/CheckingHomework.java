package com.example.twolee.chatbot.noteFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CheckingHomework {
    private static DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    // 체크 박스 누를 시에 과제 내의 isChecked 를 변화 시킴
    public static void setChecking(String homeworkUid, Boolean integer){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            String userUid = FirebaseAuth.getInstance().getUid();
            if(userUid != null && !userUid.equals(""))
                myRef.child("homeWorks").child(userUid).child(homeworkUid).child("isChecked").setValue(integer);
        }else{
            System.out.println("error");
        }
    }
}

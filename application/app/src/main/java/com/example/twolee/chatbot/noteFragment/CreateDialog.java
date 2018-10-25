package com.example.twolee.chatbot.noteFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.twolee.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateDialog{
    private String homework_uid;
    private String user_uid = FirebaseAuth.getInstance().getUid();

    public CreateDialog(String homework_uid){
        this.homework_uid = homework_uid;
    }

    // 삭제 다이얼로그 설정.
    public void showDialog(Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("과제 삭제").setMessage("정말로 삭제 하시겠습니까?").setIcon(R.drawable.ic_face_scanner).setCancelable(true)
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            FirebaseDatabase.getInstance().getReference().child("homeWorks").child(user_uid).child(homework_uid).removeValue();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        dialog.show();
    }
}

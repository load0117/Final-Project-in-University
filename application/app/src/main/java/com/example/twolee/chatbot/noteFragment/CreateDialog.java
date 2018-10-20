package com.example.twolee.chatbot.noteFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.example.twolee.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;

public class CreateDialog {
    AlertDialog.Builder dialog;
    @BindView(R.id.homework_uid)
    TextView homework_uid;

    public CreateDialog(Context context){
        dialog = new AlertDialog.Builder(context);

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
                            String userUid = FirebaseAuth.getInstance().getUid();
                            FirebaseDatabase.getInstance().getReference("homeworks").child(userUid).child(homework_uid.getText().toString()).removeValue();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
        dialog.show();
    }
}

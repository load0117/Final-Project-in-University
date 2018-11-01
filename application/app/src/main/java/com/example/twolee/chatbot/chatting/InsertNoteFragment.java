package com.example.twolee.chatbot.chatting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.login.LoginActivity;
import com.example.twolee.chatbot.noteFragment.Homework;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsertNoteFragment extends BottomSheetDialogFragment {

    @Nullable
    @BindView(R.id.write_note_btn)
    Button writeNoteBtn;
    @Nullable
    @BindView(R.id.select_cognitive_error)
    TextView selectError;
    @Nullable
    @BindView(R.id.edit_automatic_thought)
    EditText editAutomatic;
    @Nullable
    @BindView(R.id.edit_assignment)
    EditText editAssignment;
    @Nullable
    @BindView(R.id.insert_note_content)
    TextView tvContent;

    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    public InsertNoteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (firebaseUser == null) {
            Objects.requireNonNull(getContext()).startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_note, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.write_note_btn)
    public void writeNote() {
        if (selectError.getText().equals("인지적 오류 선택") || TextUtils.isEmpty(editAutomatic.getText()) || TextUtils.isEmpty(editAssignment.getText())) {
            Toast.makeText(getContext(), "빈 내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            Homework homework = new Homework(editAssignment.getText().toString(), selectError.getText().toString(), editAutomatic.getText().toString(), false, getCurrentTime());
            firebaseDatabase
                    .getReference("users/" + firebaseUser.getUid())
                    .child("tasks")
                    .push()
                    .setValue(homework)
                    .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "심리 노트에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });

        }

    }

    @OnClick(R.id.select_cognitive_error)
    public void setSelectError(View v) {
        dialogSelectOption(v.getContext());
    }

    private void dialogSelectOption(Context context) {
        final String items[] = {"흔백논리", "지나친 일반화", "낙인찌기", "내탓이오", "부정적 사고의 강조"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("인지적 오류").setCancelable(true).setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        assert selectError != null;
                        selectError.setText(items[item]);
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).create();
        builder.show();
    }

    public String getCurrentTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        System.out.println(SimpleDateFormat.getInstance());

        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd E", Locale.KOREAN);

        return sdfNow.format(date);
    }


}

package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.fragments.MyInfoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StateUpdateActivity extends AppCompatActivity{
    @BindView(R.id.state_toolbar) Toolbar state_toolbar;
    @BindView(R.id.state_message) EditText state_message;
    @BindView(R.id.stateTextLength) TextView stateTextLength;
    @BindView(R.id.updateOkButton) Button updateOkButton;

    // database
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    @NonNull private String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_state_update);

        // bind view
        ButterKnife.bind(this);
        
        getData();
        setToolbar();
        setListener();
    }

    public void getData(){
        //get data
        System.out.println(userUid);
        myRef.child("users").child(userUid).child("state").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String stateMessage = dataSnapshot.getValue(String.class);
                state_message.setText(stateMessage);

                if(stateMessage == null)
                    Log.w("nothing", "없음");
                else if(stateMessage.length() == 0)
                    stateTextLength.setText(R.string.zeroLength);
                else{
                    String totalString = stateMessage.length()+getString(R.string.maxLength);
                    stateTextLength.setText(totalString);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("failure",databaseError.toException());
            }
        });
    }

    public void setToolbar(){
        //tool bar
        setSupportActionBar(state_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        state_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
    }

    public void setListener(){
        // listener
        state_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력 하기 전에
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때
                Integer stateMessage = s.toString().length();
                if(stateMessage == 0)
                    stateTextLength.setText(R.string.zeroLength);
                else{
                    String totalString = stateMessage+getString(R.string.maxLength);
                    stateTextLength.setText(totalString);
                }

                if(stateMessage==60)
                    stateTextLength.setTextColor(Color.RED);
                else
                    stateTextLength.setTextColor(Color.BLACK);

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 입력이 끝났을 때
            }
        });

        updateOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("users").child(userUid).child("state").setValue(state_message.getText().toString());
                MyInfoFragment.newInstance();
                finish();
            }
        });
    }

}

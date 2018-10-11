package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.twolee.chatbot.R;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StateUpdateActivity extends AppCompatActivity{

    @BindView(R.id.state_message) EditText state_message;
    @BindView(R.id.stateTextLength) TextView stateTextLength;
    @BindView(R.id.updateOkButton) Button updateOkButton;
    //@BindView(R.id.profile_state) TextView profile_state;
    String message = "";
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_state_update);

        ButterKnife.bind(this);

        //message = profile_state.getText().toString();
        Intent intent = getIntent();
        message = intent.getExtras().getString("state");

        // listener

        state_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력되는 텍스트에 변화가 있을 때
                stateTextLength.setText(message.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력이 끝났을 때
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 입력 하기 전에
                state_message.setText(message);
                stateTextLength.setText(message.length());
            }
        });


    }

}

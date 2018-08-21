package com.example.twolee.chatbot.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    protected @BindView(R.id.btn_login_google)
    Button btnLoginGoogle;
    protected @BindView(R.id.btn_login_email)
    Button btnLoginEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        // 이메일로 로그인
        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);


            }
        });
    }

    public void loginSkip(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

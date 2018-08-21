package com.example.twolee.chatbot.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.twolee.chatbot.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {
    protected @BindView(R.id.edit_text_sign_email)
    EditText signEmail;
    protected @BindView(R.id.edit_text_sign_pw)
    EditText signPw;
    protected @BindView(R.id.btn_sign)
    Button sign;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        // 회원 가입
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mAuth.getInstance().createUserWithEmailAndPassword(signEmail.getText().toString(),signPw.getText().toString())
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                            }
                        });
            }
        });

    }
}

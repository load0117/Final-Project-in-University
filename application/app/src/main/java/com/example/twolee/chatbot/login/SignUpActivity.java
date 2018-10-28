package com.example.twolee.chatbot.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {
    protected @BindView(R.id.edit_text_sign_email)
    EditText signEmail;
    protected @BindView(R.id.edit_text_sign_pw)
    EditText signPw;
    protected @BindView(R.id.btn_sign)
    Button btnSign;
    protected @BindView(R.id.sign_in)
    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_sign)
    public void btnSign() {

        // 회원 가입

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(signEmail.getText().toString(), signPw.getText().toString())
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("enTrv", "signInWithEmail", task.getException());
                            if(task.getException() != null)
                                Toast.makeText(getBaseContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "회원 가입을 축하드립니다.", Toast.LENGTH_LONG).show();
                            User user = new User(signEmail.getText().toString());

                            // firebase auth 이메일에 해당하는 uid
                            String uid = task.getResult().getUser().getUid();

                            // write
                            // TODO: 18/10/2018 변경 확인
                            try{
                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(user);

                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.w("fail","로그인 실패");
                            }

                        }
                    }
                });
    }

    @OnClick(R.id.sign_in)
    public void signIn(){
        finish();
    }
}

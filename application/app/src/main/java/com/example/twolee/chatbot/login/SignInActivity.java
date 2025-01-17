package com.example.twolee.chatbot.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {
    protected @BindView(R.id.edit_text_pw)
    EditText etPw;
    protected @BindView(R.id.edit_text_email)
    EditText etEmail;
    protected @BindView(R.id.sign_up)
    TextView tvSignUp;
    protected @BindView(R.id.btn_login)
    Button btnLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        // login interface listener
        authStateListener = (firebaseAuth) -> {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("authState", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    // User is signed out
                    Log.d("authState", "onAuthStateChanged:signed_out");
                }
            };
    }

    @OnClick(R.id.sign_up)
    public void signUp(){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void buttonLogin(){
        loginEvent();
    }

    public void loginEvent(){
        if(etEmail == null || etEmail.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
        else if(etPw == null || etPw.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(),"비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
        else {
            firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPw.getText().toString()).addOnCompleteListener((task) -> {
                    // 로그인 판단
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("sign", "Sign In Fail");
                    } else {
                        Log.d("sign", "Sign In Success");
                    }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 액티비티 실행시 리스너 연결
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 액티비티 종료시 리스너 제거
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}

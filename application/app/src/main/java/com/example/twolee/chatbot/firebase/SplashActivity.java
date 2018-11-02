package com.example.twolee.chatbot.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.twolee.chatbot.BuildConfig;
import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.login.LoginActivity;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class SplashActivity extends AppCompatActivity {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        // 인앱 기본갑 설정
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        // 서버값 fetch
        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this, (task) -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SplashActivity.this, "Fetch Succeeded",
                                Toast.LENGTH_SHORT).show();

                        mFirebaseRemoteConfig.activateFetched();
                    } else {
                        Toast.makeText(SplashActivity.this, "Fetch Failed",
                                Toast.LENGTH_SHORT).show();
                    }
                    displayWelcomeMessage(); // welcome message 설정 가
                });
    }

    // splash 화면에서 welcome message 설정 함수
    // 확인차 작성
    private void displayWelcomeMessage() {
        boolean caps = mFirebaseRemoteConfig.getBoolean("splash_message_caps");
        String msg = mFirebaseRemoteConfig.getString("splash_message");

        Log.w("caps", caps + "");
        Log.w("msg", msg);
    }

    @Override
    public void onPause() {
        super.onPause();
        finish();
    }
}

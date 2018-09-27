package com.example.twolee.chatbot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.twolee.chatbot.BackButton.BackPressCloseHandler;
import com.example.twolee.chatbot.bottombar.BottomNavigationViewHelper;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.example.twolee.chatbot.fragments.HomeFragment;
import com.example.twolee.chatbot.fragments.MyInfoFragment;
import com.example.twolee.chatbot.fragments.SearchFragment;
import com.example.twolee.chatbot.scanner.ScannerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    protected @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected @BindView(R.id.toolbar_title)
    TextView mainToolbarTitle;
    protected @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.mainActivity_scanner)
    Button startSacnner;


    //처음 홈 프래그먼트가 뜨도록 초기값 설정.
    private Fragment selectedFragment;
    private FragmentTransaction transaction;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //back button push handler
        backPressCloseHandler = new BackPressCloseHandler(this);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainToolbarTitle.setText("위봇");

        // init Fragment
        selectedFragment = HomeFragment.newInstance();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment); // 프래그먼트 대체
        transaction.commit();
        startSacnner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });
        try {

            // 버튼 이벤트 추가
            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView); //이동 모드 해제
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            // 수정
                            //selectedFragment = null;
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    selectedFragment = HomeFragment.newInstance();
                                    break;
                                case R.id.action_search:
                                    selectedFragment = SearchFragment.newInstance();
                                    break;
                                case R.id.action_chatting:
                                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                    break;
                                case R.id.action_settings:
                                    selectedFragment = MyInfoFragment.newInstance();
                                    break;
                            }
                            if (selectedFragment != null) {
                                //프레그먼트 선택할 때
                                transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment);
                                transaction.commit();
                            }
                            return true;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
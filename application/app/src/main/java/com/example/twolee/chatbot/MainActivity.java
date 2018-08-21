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
import android.widget.TextView;

import com.example.twolee.chatbot.bottombar.BottomNavigationViewHelper;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.example.twolee.chatbot.fragments.HomeFragment;
import com.example.twolee.chatbot.fragments.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    protected @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected @BindView(R.id.toolbar_title)
    TextView mainToolbarTitle;
    protected @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mainToolbarTitle.setText("위봇");

        try {
            // 버튼 이벤트 추가
            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView); //이동 모드 해제
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            Fragment selectedFragment = null;
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    selectedFragment = HomeFragment.newInstance();
                                    break;
                                case R.id.action_search:
                                    selectedFragment = SearchFragment.newInstance();
                                    break;
                                case R.id.action_chatting:
                                    Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    // TODO: 2018. 8. 13. 어떤 FLAG를 사용할 지 생각
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                    break;
                                case R.id.action_settings:
                                    break;
                            }
                            if (selectedFragment != null) {
                                //프레그먼트 선택할 때#
                                // TODO: 2018. 8. 14. 프래그먼트 안의 내용물 구조는 어떻게 작성할 것인가
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment); // 프래그먼트 대체
                                transaction.commit();
                            }
                            return true;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
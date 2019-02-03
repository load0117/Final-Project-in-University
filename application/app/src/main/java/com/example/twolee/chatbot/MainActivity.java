package com.example.twolee.chatbot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.twolee.chatbot.backButton.BackPressCloseHandler;
import com.example.twolee.chatbot.chatting.ChatActivity;
import com.example.twolee.chatbot.helper.BottomNavigationBehavior;
import com.example.twolee.chatbot.helper.BottomNavigationViewHelper;
import com.example.twolee.chatbot.mainFragment.HomeFragment;
import com.example.twolee.chatbot.myPageFragment.MyInfoFragment;
import com.example.twolee.chatbot.noteFragment.NoteFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    protected @BindView(R.id.toolbar)
    Toolbar toolbar;
    protected @BindView(R.id.toolbar_title)
    TextView mainToolbarTitle;
    protected @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    //처음 홈 프래그먼트가 뜨도록 초기값 설정.
    private Fragment selectedFragment;
    private FragmentTransaction transaction;

    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        ButterKnife.bind(this);

        //back button push handler
        backPressCloseHandler = new BackPressCloseHandler(this);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        //toolbar
        setSupportActionBar(toolbar);
        mainToolbarTitle.setText(R.string.mainToolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        // init Fragment
        selectedFragment = HomeFragment.newInstance();

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment); // 프래그먼트 대체
        transaction.commit();

        try {

            // 버튼 이벤트 추가
            BottomNavigationViewHelper.disableShiftMode(bottomNavigationView); //이동 모드 해제
            bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
                // 수정
                selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        mainToolbarTitle.setText(R.string.mainToolbar);
                        selectedFragment = HomeFragment.newInstance();
                        break;
                    case R.id.action_note:
                        mainToolbarTitle.setText("심리노트");
                        selectedFragment = NoteFragment.newInstance();
                        break;
                    case R.id.action_chatting:
                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(intent);
                        break;
                    case R.id.action_settings:
                        mainToolbarTitle.setText("더보기");
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
            });
        }catch (Exception e){
            Log.w("Error Msg", e.toString());
        }
    }
}
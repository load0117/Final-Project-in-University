package com.example.twolee.chatbot.noteFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.example.twolee.chatbot.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends Activity{

    @BindView(R.id.homework_detail_type)
    TextView homework_detail_type;
    @BindView(R.id.homework_detail_assignment)
    TextView homework_detail_assignment;
    @BindView(R.id.homework_detail_recent_string)
    TextView homework_detail_recentString;


    String assignment ="" , homework_type = "", recentString = "";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        assignment = intent.getStringExtra("assignment");
        homework_type = intent.getStringExtra("homework_type");
        recentString = intent.getStringExtra("recentString");

        homework_detail_type.setText(homework_type);
        homework_detail_recentString.setText(recentString);
        homework_detail_assignment.setText(assignment);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

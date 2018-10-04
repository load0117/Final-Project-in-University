package com.example.twolee.chatbot.scanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.twolee.chatbot.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

//import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.Tone;

public class WriteTextActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView writeToolbarTitle;
    @BindView(R.id.btn_analyze)
    Button analyzeButton;
    @BindView(R.id.user_input)
    EditText userInput;
    @BindString(R.string.tone_analyzer_username)
    String username;
    @BindString(R.string.tone_analyzer_password)
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_text);
        ButterKnife.bind(this);
        // toolbar
        setSupportActionBar(toolbar);
        writeToolbarTitle.setText("텍스트 작성");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 뒤로가기 이벤트
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WriteTextActivity.this, ScannerActivity.class));
                finish();
            }
        });
        // tone analyzer
        /*
        try {
            final ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-07-01");
            toneAnalyzer.setUsernameAndPassword(username, password);

            analyzeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String textToAnalyze = userInput.getText().toString();

                    ToneOptions options = new ToneOptions.Builder()
                            .addTone(Tone.EMOTION)
                            .html(false).build();

                    toneAnalyzer.getTone(textToAnalyze, options).enqueue(new ServiceCallback<ToneAnalysis>() {
                        @Override
                        public void onResponse(ToneAnalysis response) {
                            List<ToneScore> scores = response.getDocumentTone().getTones()
                                    .get(0).getTones();

                            String detectedTones = "";
                            for (ToneScore score : scores) {
                                if (score.getScore() > 0.5f) {
                                    detectedTones += score.getName() + " ";
                                }
                            }

                            final String toastMessage = "다음과 같은 감정이 감지되었습니다.:\n\n"
                                    + detectedTones.toUpperCase();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getBaseContext(),
                                            toastMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onFailure(Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
}

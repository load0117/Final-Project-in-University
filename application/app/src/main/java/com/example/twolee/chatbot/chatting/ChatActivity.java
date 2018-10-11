package com.example.twolee.chatbot.chatting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.model.Message;
import com.example.twolee.chatbot.model.parse.GSONParse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    com.ibm.watson.developer_cloud.assistant.v1.model.Context context = null;
    private final int RESPONSE_NUM = 10;
    private final int OPTION_NUM = 2;
    private boolean initialRequest;
    private ChatAdapter mAdapter;
    private ArrayList messageArrayList;
    private String[] labels;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.message)
    EditText inputMessage;
    @BindView(R.id.btn_send)
    ImageView btnSend;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView chatToolbarTitle;
    @BindString(R.string.waston_assistant_workspacesId)
    //@BindString(R.string.hk_workspaceId)
    String workspacesId;
    @BindString(R.string.watson_assistant_username)
    String watsonUsername;
    @BindString(R.string.watson_assistant_password)
    String watsonPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);
        ButterKnife.bind(this);

        setupToolbar();
        setupRecyclerView();

        labels = new String[OPTION_NUM];
        this.inputMessage.setText("");
        this.initialRequest = true;
        sendMessage();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternetConnection()) {
                    sendMessage();
                }
            }
        });


    }

    private void setupToolbar() {
        // toolbar
        setSupportActionBar(toolbar);
        chatToolbarTitle.setText("위봇");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 뒤로가기 이벤트
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setupRecyclerView() {
        messageArrayList = new ArrayList<>();
        mAdapter = new ChatAdapter(messageArrayList, new ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                if (mAdapter.getItemId(position) == 4) {
                    ChatActivity.this.inputMessage.setText(labels[0]);
                    sendMessage();
                }else{
                    ChatActivity.this.inputMessage.setText(labels[1]);
                    sendMessage();
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    // Sending a message to Watson Conversation Service
    private void sendMessage() {

        final String strInputMessage = this.inputMessage.getText().toString().trim();
        if (!this.initialRequest) {
            Message inputMessage = new Message();
            inputMessage.setMessage(strInputMessage);
            inputMessage.setId("1");
            messageArrayList.add(inputMessage);
            Log.i("input message", strInputMessage);
        } else {
            Message inputMessage = new Message();
            inputMessage.setMessage(strInputMessage);
            inputMessage.setId("100");
            this.initialRequest = false;
            Log.i("init watson", "왓슨 인사 말 받아오기.");
        }

        this.inputMessage.setText("");
        mAdapter.notifyDataSetChanged();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {

                    Assistant service = new Assistant("2018-09-20");
                    service.setUsernameAndPassword(watsonUsername, watsonPassword);
                    InputData input = new InputData.Builder(strInputMessage).build();
                    Log.i("input Message Text", strInputMessage);
                    MessageOptions options = new MessageOptions
                            .Builder(workspacesId)
                            .input(input)
                            .context(context)
                            .build();
                    MessageResponse response = service.message(options).execute();

                    //Passing Context of last conversation
                    if (response.getContext() != null) {
                        context = response.getContext();
                    }
                    Log.i("context", context.toString());
                    Log.i("response", response.toString());

                    if (response.getOutput() != null && response.getOutput().containsKey("text")) {
                        ArrayList<String> responseList = (ArrayList) response.getOutput().get("text");
                        Log.i("responseList Text", responseList.toString());

                        Message[] outMessage = new Message[RESPONSE_NUM];
                        if (responseList != null && responseList.size() > 0) {
                            for (int i = 0; i < responseList.size(); i++) {
                                outMessage[i] = new Message();
                                outMessage[i].setMessage(responseList.get(i));
                                outMessage[i].setId("2");
                                Log.i("outMessage", responseList.get(i));
                                messageArrayList.add(outMessage[i]);
                            }
                        } else {
                            //TODO: response type = options
                            String strGeneric = response.getOutput().getGeneric().toString();
                            Log.i("repsonse generic", strGeneric);

                            List<GSONParse> parseList = new Gson().fromJson(strGeneric, new TypeToken<List<GSONParse>>() {
                            }.getType());
                            Log.i("pare list title", parseList.get(0).getTitle());
                            Log.i("pare list label", parseList.get(0).getOptions().get(0).getLabel());
                            Log.i("pare list text", parseList.get(0).getOptions().get(0).getValue().getInput().getText());
                            Log.i("pare list", String.valueOf(parseList.size()));

                            for (int i = 0; i < parseList.get(0).getOptions().size(); i++) {
                                labels[i] = parseList.get(0).getOptions().get(i).getLabel();
                            }
                            for (int i = 0; i < parseList.size(); i++) {
                                outMessage[i] = new Message();
                                outMessage[i].setTitle(parseList.get(i).getTitle());
                                outMessage[i].setLabels(labels);
                                outMessage[i].setId("3");
                                messageArrayList.add(outMessage[i]);
                            }


                        }
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            if (mAdapter.getItemCount() > 1) {
                                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, mAdapter.getItemCount() - 1);
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private boolean checkInternetConnection() {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Check for network connections
        if (isConnected) {
            return true;
        } else {
            Toast.makeText(this, " 인터넷 연결을 확인해주세요. ", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}


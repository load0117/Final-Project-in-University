package com.example.twolee.chatbot.chatting;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.twolee.chatbot.R;
import com.example.twolee.chatbot.model.Message;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int SELF = 100;
    private final int OPTION = 300;
    private final ClickListener listener;
    private ArrayList<Message> messageArrayList;
    private final int[] BUTTONS = {
            R.id.btn_options1, R.id.btn_options2, R.id.btn_options3, R.id.btn_options4, R.id.btn_options5
    };

    public ChatAdapter(ArrayList<Message> messageArrayList, ClickListener listener) {
        this.messageArrayList = messageArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        // view type is to identify where to render the chat message
        // left or right
        if (viewType == SELF) {
            // self message
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_item_self, parent, false);
        } else {
            // WeBot message
            if (viewType == OPTION) {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_select_options, parent, false);
            } else {
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_item_watson, parent, false);
            }
        }

        return new ViewHolder(itemView, listener);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageArrayList.get(position);
        if (message.getId() != null && message.getId().equals("1")) {
            return SELF;
        }
        if (message.getId() != null && message.getId().equals("3")) {
            return OPTION;
        }

        return position;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Message message = messageArrayList.get(position);
        if (holder.getItemViewType() == OPTION) {
            message.setTitle(message.getTitle());
            ((ViewHolder) holder).message.setText(message.getTitle());

            String[] labels = new String[10];
            for (int i = 0; i < message.getLabels().length; i++) {
                labels[i] = message.getLabels()[i];
            }
            message.setLabels(labels);
            ((ViewHolder) holder).button1.setText(message.getLabels()[0]);
            ((ViewHolder) holder).button2.setText(message.getLabels()[1]);
        } else {
            message.setMessage(message.getMessage());
            ((ViewHolder) holder).message.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.message)
        TextView message;
        @Nullable
        @BindView(R.id.btn_options1)
        Button button1;
        @Nullable
        @BindView(R.id.btn_options2)
        Button button2;

        private WeakReference<ClickListener> listenerRef;

        public ViewHolder(View view, ClickListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            listenerRef = new WeakReference<>(listener);
            if (button1 != null && button2 != null) {
                button1.setOnClickListener(this);
                button2.setOnClickListener(this);

            }
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onClick(v);
        }
    }

}
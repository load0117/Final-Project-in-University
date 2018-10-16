package com.example.twolee.chatbot.psycological;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twolee.chatbot.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ReviewViewHolder> {

    private List<Homework> homeworkList;

    public HomeworkAdapter(){
        homeworkList = new ArrayList<>();

        for(int i=0; i<20; i++)
            homeworkList.add(new Homework());
    }

    @Override
    public int getItemCount() {
        //아이템의 개수를 구하는 부분
        return homeworkList.size();
    }

    @Override
    public HomeworkAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 뷰 홀더 생성시 호출 어떻게 생성해서 무엇을 전달할 것인가. 새롭게 생성할 때만 호출된다.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_card_show, parent, false);
        return new HomeworkAdapter.ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.homework_title)
        TextView homework_title;
        @BindView(R.id.homework_finished_button)
        CheckBox homework_finished_button;
        @BindView(R.id.homework_form)
        LinearLayout homework_form;

        public ReviewViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);

            setListener();
        }

        private void setListener(){
            homework_finished_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        // 체크 하면
                        Toast.makeText(buttonView.getContext(), "완료", Toast.LENGTH_SHORT).show();
                        // 불투명 하게
                        homework_form.setAlpha((float)0.5);
                    }else{
                        // 최크 해제하면
                        Toast.makeText(buttonView.getContext(), "해제", Toast.LENGTH_SHORT).show();
                        homework_form.setAlpha((float)1.0);
                    }
                }
            });
        }


    }
}

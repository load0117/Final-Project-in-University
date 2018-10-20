package com.example.twolee.chatbot.noteFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twolee.chatbot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ReviewViewHolder> {

    private List<Homework> homeworkList;
    private List<String> homeworkUidList;

    public HomeworkAdapter(List<Homework> homeworkList, List<String> homeworkUidList){
        this.homeworkList = homeworkList;
        this.homeworkUidList = homeworkUidList;
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
        Homework newHomework = homeworkList.get(position);
        String homeworkUid = homeworkUidList.get(position);
        holder.setData(newHomework,homeworkUid);
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.homework_goal)
        TextView homework_goal;
        @BindView(R.id.homework_finished_button)
        CheckBox homework_finished_button;
        @BindView(R.id.homework_form)
        LinearLayout homework_form;
        @BindView(R.id.homework_uid)
        TextView homework_uid;
        @BindView(R.id.remove_button)
        Button removeButton;

        public ReviewViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);

            setListener();
        }

        public void setData(Homework newHomework, String uid){
            homework_goal.setText(newHomework.getGoal());
            homework_uid.setText(uid);
        }

        private void setListener(){
            homework_finished_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        // 체크 하면
                        Log.w("finish", "완료");
                        // 불투명 하게
                        homework_form.setAlpha((float)0.5);

                        // 삭제 버튼 생성
                        removeButton.setVisibility(View.VISIBLE);
                        removeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CreateDialog dialog = new CreateDialog(v.getContext());
                            }
                        });
                    }else{
                        // 체크 해제하면
                        Log.w("notFinish", "해제");
                        CheckingHomework checkingHomework = CheckingHomework.getInstance();
                        checkingHomework.setChecking(homework_uid.getText().toString(),true);
                        homework_form.setAlpha((float)1.0);
                        removeButton.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }


    }
}

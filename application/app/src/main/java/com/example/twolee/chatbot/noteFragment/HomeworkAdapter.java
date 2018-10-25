package com.example.twolee.chatbot.noteFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twolee.chatbot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

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

    @NonNull
    @Override
    public HomeworkAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        private ReviewViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);
        }

        private void setData(Homework newHomework, String uid){
            // Insert Data
            homework_goal.setText(newHomework.getGoal());
            homework_uid.setText(uid);
            // layout to check box button
            if(newHomework.getIsChecked()) {
                homework_finished_button.setChecked(true);
                visRemoveButton();
            }else{
                homework_finished_button.setChecked(false);
                noRemoveButton();
            }
        }


        @OnCheckedChanged(R.id.homework_finished_button)
        public void isChecking(boolean isChecked) {
            if (isChecked) {
                // 체크 하면
                Log.w("finish", "완료");
                // 불투명 하게
                visRemoveButton();
                // 체크했다고 디비에 연락
                CheckingHomework.setChecking(homework_uid.getText().toString(), true);
            } else {
                // 체크 해제하면
                Log.w("release", "해제");
                noRemoveButton();
                // 체크 헤제 했다고 디비에 연락
                CheckingHomework.setChecking(homework_uid.getText().toString(), false);
            }
        }

        // 체크 버튼 누르면 삭제 버튼 생성 및 화면 불투명 하게
        private void visRemoveButton(){
            homework_form.setAlpha((float)0.5);
            removeButton.setVisibility(View.VISIBLE);
        }

        @OnClick(R.id.remove_button)
        public void toRemove(View v){
            CreateDialog dialog = new CreateDialog(homework_uid.getText().toString());
            dialog.showDialog(v.getContext());
        }

        // 체크 버튼 누르지 않았으면 삭제 버튼 제거 및 화면 원래대로
        private void noRemoveButton(){
            homework_form.setAlpha((float)1.0);
            removeButton.setVisibility(View.INVISIBLE);
        }
    }
}

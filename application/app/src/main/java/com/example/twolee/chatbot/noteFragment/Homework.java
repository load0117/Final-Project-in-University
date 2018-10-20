package com.example.twolee.chatbot.noteFragment;

public class Homework {

    /*
        과제 form V1.1

        - 추가 사항

        ischecked;
     */

    private String goal;
    private String startTime;
    private String endTime;
    private boolean isChecked;

    public Homework(){}

    public Homework(String goal, String startTime, String endTime){
        setGoal(goal);
        setStartTime(startTime);
        setEndTime(endTime);

    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

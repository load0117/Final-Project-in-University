package com.example.twolee.chatbot.noteFragment;

public class Homework {

    /*
        과제 form V1.12

        - 추가 사항

        isChecked 변수

        getter / setter 명명
     */

    private String goal;
    private String startTime;
    private String endTime;
    private Boolean isChecked = false;

    public Homework(){}

    public Homework(String goal, String startTime, String endTime, Boolean isChecked){
        setGoal(goal);
        setStartTime(startTime);
        setEndTime(endTime);
        setIsChecked(isChecked);
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

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean checked) {
        isChecked = checked;
    }
}

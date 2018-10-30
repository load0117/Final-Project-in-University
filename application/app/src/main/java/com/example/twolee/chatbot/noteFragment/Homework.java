package com.example.twolee.chatbot.noteFragment;

public class Homework {

    /*
        과제 form V1.14

        - 추가 사항

        startTime , endTime 제거

        type 추가
        recent string 추가
     */

    private String goal;
    private Boolean isChecked = false;
    private String recentString;
    private String type;

    public Homework(){}

    public Homework(String goal, Boolean isChecked, String recentString, String type){
        setGoal(goal);
        setIsChecked(isChecked);
        setRecentString(recentString);
        setType(type);
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecentString() {
        return recentString;
    }

    public void setRecentString(String recentString) {
        this.recentString = recentString;
    }

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean checked) {
        isChecked = checked;
    }
}

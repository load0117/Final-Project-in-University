package com.example.twolee.chatbot.noteFragment;

public class Homework {

    /*
        과제 form V1.13

        - 추가 사항

        startTime , endTime 제거

        type 추가
     */

    private String goal;
    private String type;
    private Boolean isChecked = false;

    public Homework(){}

    public Homework(String goal, String type, Boolean isChecked){
        setGoal(goal);
        setType(type);
        setIsChecked(isChecked);
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

    public Boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(Boolean checked) {
        isChecked = checked;
    }
}

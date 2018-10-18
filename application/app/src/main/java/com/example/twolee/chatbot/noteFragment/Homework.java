package com.example.twolee.chatbot.noteFragment;

public class Homework {
    private String goal;
    private String startTime;
    private String endTime;

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
}

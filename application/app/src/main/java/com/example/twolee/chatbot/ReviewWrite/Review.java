package com.example.twolee.chatbot.ReviewWrite;

import android.widget.ProgressBar;
import android.widget.TextView;

public class Review {
    /*
        Review 내용 V 1.0

        객체가 가지는 정보

        사용자의 ID.
        사용자의 리뷰 내용.
        리뷰 평점.
     */
    private String id;
    private String contents;

    private double rating;

    public Review(){}

    public Review(String id, String contents, double rating){
        setId(id);
        setContents(contents);
        setRating(rating);
    }

    //getter, setter

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setContents(String contents){
        this.contents = contents;
    }

    public String getContents(){
        return this.contents;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    // TODO: 2018. 8. 31. 작성 시간 확인
}

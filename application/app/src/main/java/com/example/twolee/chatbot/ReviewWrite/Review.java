package com.example.twolee.chatbot.ReviewWrite;

public class Review {
    /*
        Review 내용 V 1.2

        객체가 가지는 정보

        리뷰 UID.
        사용자 UID.
        사용자의 리뷰 내용.
        리뷰 평점.
        작성 시간.
     */
    private String reviewNum;
    private String uid;
    private String contents;
    private String rating;
    private String writtenTime;

    public Review(){}

    public Review(String reviewNum, String uid, String contents, String rating, String writtenTime){
        setReviewNum(reviewNum);
        setUid(uid);
        setContents(contents);
        setRating(rating);
        setWrittenTime(writtenTime);
    }

    //getter, setter

    public void setReviewNum(String reviewNum) {
        this.reviewNum = reviewNum;
    }

    public String getReviewNum() {
        return reviewNum;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setContents(String contents){
        this.contents = contents;
    }

    public String getContents(){
        return this.contents;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setWrittenTime(String writtenTime) {
        this.writtenTime = writtenTime;
    }

    public String getWrittenTime() {
        return writtenTime;
    }
}

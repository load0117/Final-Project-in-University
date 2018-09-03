package com.example.twolee.chatbot.ReviewWrite;

public class Review {
    /*
        Review 내용 V 1.1

        객체가 가지는 정보

        사용자의 ID.
        사용자의 리뷰 내용.
        리뷰 평점.
     */
    private String reviewNum;
    private String id;
    private String contents;
    private String rating;
    private String writtenTime;

    public Review(){}

    public Review(String reviewNum, String id, String contents, String rating, String writtenTime){
        setReviewNum(reviewNum);
        setId(id);
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

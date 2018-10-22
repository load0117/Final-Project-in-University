package com.example.twolee.chatbot.mainFragment;

public class Review {
    /*
        Review 내용 V 1.31

        객체가 가지는 정보

        리뷰 UID.
        사용자 UID.
        사용자 username.
        사용자 리뷰 내용.
        리뷰 평점.
        작성 시간.
        좋아요 개수.

        변경 사항 : 생성자 변수 받는 방식 변경
     */
    private String userUid;
    private String username;
    private String contents;
    private String rating;
    private String writtenTime;
    private long like;

    public Review(){}

    public Review(String userUid, String username, String contents, String rating, String writtenTime, long like){
        this.userUid = userUid;
        this.username = username;
        this.contents = contents;
        this.rating = rating;
        this.writtenTime = writtenTime;
        this.like = like;
    }

    //getter, setter

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public void setLike(long like) {
        this.like = like;
    }

    public long getLike() {
        return like;
    }
}

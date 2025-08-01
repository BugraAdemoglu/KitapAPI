package com.kitapkulubu.kitapapi.dto;

public class RateRequestDTO {

    private int score;
    private Long userId;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
package com.kitapkulubu.kitapapi.dto;

public class CommentRequestDTO {

    private String text;

    private int score;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
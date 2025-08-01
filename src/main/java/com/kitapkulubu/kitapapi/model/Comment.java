package com.kitapkulubu.kitapapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private int score;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("comments")
    @JsonBackReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("comments")
    private User user;

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}


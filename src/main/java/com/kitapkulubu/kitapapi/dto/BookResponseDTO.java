package com.kitapkulubu.kitapapi.dto;

import java.util.List;

public class BookResponseDTO {
    private Long id;
    private String title;
    private String description;
    private int publicationYear;
    private String authorName;
    private String publishHouseName;
    private double averageRating;
    private List<CommentDTO> comments;


    public BookResponseDTO(Long id, String title, String description, int publicationYear, String authorName,
                           String publishHouseName, double averageRating, List<CommentDTO> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
        this.authorName = authorName;
        this.publishHouseName = publishHouseName;
        this.averageRating = averageRating;
        this.comments = comments;
    }

    public BookResponseDTO() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getPublishHouseName() { return publishHouseName; }
    public void setPublishHouseName(String publishHouseName) { this.publishHouseName = publishHouseName; }
    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    public List<CommentDTO> getComments() { return comments; }
    public void setComments(List<CommentDTO> comments) { this.comments = comments; }
}
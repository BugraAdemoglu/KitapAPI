
package com.kitapkulubu.kitapapi.dto;

public class BookDTO {

        private Long id;
        private String title;
        private String description;
        private int publicationYear;
        private Long authorId;
        private Long publishHouseId;
        private double rating;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getPublishHouseId() {
        return publishHouseId;
    }

    public void setPublishHouseId(Long publishHouseId) {
        this.publishHouseId = publishHouseId;
    }
}


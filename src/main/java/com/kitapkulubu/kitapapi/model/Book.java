
//book model;
package com.kitapkulubu.kitapapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private int publicationYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("books")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "publish_house_id")
    @JsonIgnoreProperties("books")
    private PublishHouse publishHouse;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rate> rates;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
    public PublishHouse getPublishHouse() { return publishHouse; }
    public void setPublishHouse(PublishHouse publishHouse) { this.publishHouse = publishHouse; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public List<Rate> getRates() { return rates; }
    public void setRates(List<Rate> rates) { this.rates = rates; }
}
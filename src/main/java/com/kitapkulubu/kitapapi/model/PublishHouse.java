package com.kitapkulubu.kitapapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class PublishHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "publishHouse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Book> books;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
}
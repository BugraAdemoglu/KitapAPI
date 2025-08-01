package com.kitapkulubu.kitapapi.service;

import com.kitapkulubu.kitapapi.model.Author;
import com.kitapkulubu.kitapapi.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setBiography(updatedAuthor.getBiography());
        return authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
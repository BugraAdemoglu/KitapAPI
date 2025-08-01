package com.kitapkulubu.kitapapi.service;

import com.kitapkulubu.kitapapi.Repository.AuthorRepository;
import com.kitapkulubu.kitapapi.Repository.PublishHouseRepository;
import com.kitapkulubu.kitapapi.dto.BookDTO;
import com.kitapkulubu.kitapapi.dto.BookResponseDTO;
import com.kitapkulubu.kitapapi.dto.CommentDTO;
import com.kitapkulubu.kitapapi.model.*;
import com.kitapkulubu.kitapapi.Repository.BookRepository;
import com.kitapkulubu.kitapapi.specification.BookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublishHouseRepository publishHouseRepository;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublishHouseRepository publishHouseRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publishHouseRepository = publishHouseRepository;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + bookDTO.getAuthorId()));
        PublishHouse publishHouse = publishHouseRepository.findById(bookDTO.getPublishHouseId())
                .orElseThrow(() -> new RuntimeException("PublishHouse not found with id: " + bookDTO.getPublishHouseId()));

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setAuthor(author);
        book.setPublishHouse(publishHouse);

        return bookRepository.save(book);
    }

    public Book updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        PublishHouse publishHouse = publishHouseRepository.findById(bookDTO.getPublishHouseId())
                .orElseThrow(() -> new RuntimeException("Publish house not found"));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setDescription(bookDTO.getDescription());
        existingBook.setPublicationYear(bookDTO.getPublicationYear());
        existingBook.setAuthor(author);
        existingBook.setPublishHouse(publishHouse);

        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookResponseDTO> getBooksAsDTO(Long authorId, Long publishHouseId) {
        Specification<Book> spec = Specification
                .where(BookSpecification.hasAuthor(authorId))
                .and(BookSpecification.hasPublishHouse(publishHouseId));

        Sort sortOrder = Sort.by(Sort.Direction.DESC, "id");

        return bookRepository.findAll(spec, sortOrder).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getBooksSortedByAverageRating() {
        List<Book> books = bookRepository.findAll();

        books.sort((b1, b2) -> Double.compare(
                calculateAverageRating(b2),
                calculateAverageRating(b1)
        ));

        return books.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<BookResponseDTO> getBookByIdAsDTO(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToResponseDTO);
    }

    private BookResponseDTO convertToResponseDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPublicationYear(book.getPublicationYear());

        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getName());
        }
        if (book.getPublishHouse() != null) {
            dto.setPublishHouseName(book.getPublishHouse().getName());
        }

        dto.setAverageRating(calculateAverageRating(book));

        if (book.getComments() != null) {
            dto.setComments(book.getComments().stream()
                    .map(this::convertCommentToDTO)
                    .collect(Collectors.toList()));
        } else {
            dto.setComments(Collections.emptyList());
        }

        return dto;
    }

    private double calculateAverageRating(Book book) {
        // Ortalama puanı, artık var olmayan Rate'ler yerine Comment'lerin içindeki score'lardan hesaplıyoruz.
        if (book.getComments() == null || book.getComments().isEmpty()) {
            return 0.0;
        }
        return book.getComments().stream()
                .filter(comment -> comment.getScore() > 0)
                .mapToInt(Comment::getScore)
                .average()
                .orElse(0.0);
    }

    private CommentDTO convertCommentToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getContent());

        // ratingi karmaşık olmasın diye yorumlar beraber aldım.DEĞERLENDİR.
        dto.setUserScore(comment.getScore());

        User user = comment.getUser();
        if (user != null) {
            dto.setUserName(user.getUsername());
        } else {
            dto.setUserName("Anonim");
        }
        return dto;
    }
}
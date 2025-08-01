package com.kitapkulubu.kitapapi.controller;

import com.kitapkulubu.kitapapi.dto.BookDTO;
import com.kitapkulubu.kitapapi.dto.BookResponseDTO;
import com.kitapkulubu.kitapapi.model.Book;
import com.kitapkulubu.kitapapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long publishHouseId
    ) {
        List<BookResponseDTO> books;

        if ("rating".equals(sort)) {
            books = bookService.getBooksSortedByAverageRating();
        } else {
            books = bookService.getBooksAsDTO(authorId, publishHouseId);
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        return bookService.getBookByIdAsDTO(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody BookDTO bookDTO) {
        Book savedBook = bookService.saveBook(bookDTO);
        return new ResponseEntity<>(bookService.getBookByIdAsDTO(savedBook.getId()).get(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(bookService.getBookByIdAsDTO(updatedBook.getId()).get());
    }
}
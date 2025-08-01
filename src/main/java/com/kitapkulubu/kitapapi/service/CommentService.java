package com.kitapkulubu.kitapapi.service;

import com.kitapkulubu.kitapapi.Repository.BookRepository;
import com.kitapkulubu.kitapapi.Repository.CommentRepository;
import com.kitapkulubu.kitapapi.Repository.UserRepository;
import com.kitapkulubu.kitapapi.dto.CommentRequestDTO;
import com.kitapkulubu.kitapapi.model.Book;
import com.kitapkulubu.kitapapi.model.Comment;
import com.kitapkulubu.kitapapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    public Comment addCommentToBook(Long bookId, Long userId, CommentRequestDTO commentRequest) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Optional<Comment> existingCommentOpt = commentRepository.findByBookAndUser(book, user);

        Comment commentToSave;
        if (existingCommentOpt.isPresent()) {

            commentToSave = existingCommentOpt.get();
            commentToSave.setContent(commentRequest.getText());
            commentToSave.setScore(commentRequest.getScore());
        } else {

            commentToSave = new Comment();
            commentToSave.setBook(book);
            commentToSave.setUser(user);
            commentToSave.setContent(commentRequest.getText());
            commentToSave.setScore(commentRequest.getScore());
        }

        return commentRepository.save(commentToSave);
    }

    public List<Comment> getCommentsByBookId(Long bookId) {
        return commentRepository.findByBookId(bookId);
    }
}
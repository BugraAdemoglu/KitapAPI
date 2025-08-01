package com.kitapkulubu.kitapapi.controller;

import com.kitapkulubu.kitapapi.dto.CommentDTO;
import com.kitapkulubu.kitapapi.dto.CommentRequestDTO;
import com.kitapkulubu.kitapapi.model.Comment;
import com.kitapkulubu.kitapapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books/{bookId}/comments/{userId}")
@CrossOrigin
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody CommentRequestDTO commentRequest) {
        Comment savedComment = commentService.addCommentToBook(bookId,userId, commentRequest);
        return new ResponseEntity<>(convertToDTO(savedComment), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long bookId) {
        List<Comment> comments = commentService.getCommentsByBookId(bookId);

        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    private CommentDTO convertToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getContent());
        if (comment.getUser() != null) {
            dto.setUserName(comment.getUser().getUsername());
        } else {
            dto.setUserName("Anonim");
        }
        return dto;
    }
}
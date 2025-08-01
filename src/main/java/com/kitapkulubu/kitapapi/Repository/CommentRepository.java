package com.kitapkulubu.kitapapi.Repository;

import com.kitapkulubu.kitapapi.model.Book;
import com.kitapkulubu.kitapapi.model.Comment;
import com.kitapkulubu.kitapapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookId(Long bookId);
    Optional<Comment> findByBookAndUser(Book book, User user);
}

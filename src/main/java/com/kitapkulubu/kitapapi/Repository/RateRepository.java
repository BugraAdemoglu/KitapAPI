package com.kitapkulubu.kitapapi.Repository;

import com.kitapkulubu.kitapapi.model.Book;
import com.kitapkulubu.kitapapi.model.Rate;
import com.kitapkulubu.kitapapi.model.User; // <-- DOĞRU USER IMPORT'U
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    List<Rate> findByBookId(Long bookId);
    Optional<Rate> findByBookAndUser(Book book, User user);

}
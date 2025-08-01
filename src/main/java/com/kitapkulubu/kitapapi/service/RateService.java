package com.kitapkulubu.kitapapi.service;

import com.kitapkulubu.kitapapi.Repository.BookRepository;
import com.kitapkulubu.kitapapi.Repository.RateRepository;
import com.kitapkulubu.kitapapi.Repository.UserRepository;
import com.kitapkulubu.kitapapi.dto.RateRequestDTO;
import com.kitapkulubu.kitapapi.model.Book;
import com.kitapkulubu.kitapapi.model.Rate;
import com.kitapkulubu.kitapapi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class RateService {

    private final RateRepository rateRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public RateService(RateRepository rateRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.rateRepository = rateRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Rate addOrUpdateRate(Long bookId, RateRequestDTO rateRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        User user = userRepository.findById(rateRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + rateRequest.getUserId()));

        Optional<Rate> existingRateOpt = rateRepository.findByBookAndUser(book, user);

        Rate rate;
        if (existingRateOpt.isPresent()) {

            rate = existingRateOpt.get();
            rate.setScore(rateRequest.getScore());
        } else {
            rate = new Rate();
            rate.setBook(book);
            rate.setUser(user);
            rate.setScore(rateRequest.getScore());
        }

        return rateRepository.save(rate);
    }
}
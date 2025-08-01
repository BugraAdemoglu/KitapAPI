
package com.kitapkulubu.kitapapi.controller;

import com.kitapkulubu.kitapapi.dto.RateRequestDTO;
import com.kitapkulubu.kitapapi.model.Rate;
import com.kitapkulubu.kitapapi.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books/{bookId}/rate")
@CrossOrigin
public class RateController {

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping
    public ResponseEntity<Void> addOrUpdateRate(@PathVariable Long bookId, @RequestBody RateRequestDTO rateRequest) {
        rateService.addOrUpdateRate(bookId, rateRequest);
        return ResponseEntity.ok().build();
    }
}
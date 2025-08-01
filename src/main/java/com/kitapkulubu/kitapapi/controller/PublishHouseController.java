package com.kitapkulubu.kitapapi.controller;

import com.kitapkulubu.kitapapi.model.PublishHouse;
import com.kitapkulubu.kitapapi.service.PublishHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publishhouses")
@CrossOrigin
public class PublishHouseController {

    @Autowired
    private PublishHouseService publishHouseService;

    @PostMapping
    public PublishHouse addPublishHouse(@RequestBody PublishHouse publishHouse) {
        return publishHouseService.savePublishHouse(publishHouse);
    }

    @GetMapping
    public List<PublishHouse> getAllPublishHouses() {
        return publishHouseService.getAllPublishHouses();
    }

    @GetMapping("/{id}")
    public Optional<PublishHouse> getPublishHouseById(@PathVariable Long id) {
        return publishHouseService.getPublishHouseById(id);
    }

    @PutMapping("/{id}")
    public Optional<PublishHouse> updatePublishHouse(@PathVariable Long id, @RequestBody PublishHouse updated) {
        return publishHouseService.updatePublishHouse(id, updated);
    }

    @DeleteMapping("/{id}")
    public void deletePublishHouse(@PathVariable Long id) {
        publishHouseService.deletePublishHouse(id);
    }
}

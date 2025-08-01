package com.kitapkulubu.kitapapi.service;

import com.kitapkulubu.kitapapi.Repository.PublishHouseRepository;
import com.kitapkulubu.kitapapi.model.PublishHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublishHouseService {

    @Autowired
    private PublishHouseRepository publishHouseRepository;

    public List<PublishHouse> getAllPublishHouses() {
        return publishHouseRepository.findAll();
    }

    public Optional<PublishHouse> getPublishHouseById(Long id) {
        return publishHouseRepository.findById(id);
    }

    public PublishHouse savePublishHouse(PublishHouse publishHouse) {
        return publishHouseRepository.save(publishHouse);
    }

    public Optional<PublishHouse> updatePublishHouse(Long id, PublishHouse updated) {
        return publishHouseRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setAddress(updated.getAddress());
            return publishHouseRepository.save(p);
        });
    }

    public void deletePublishHouse(Long id) {
        publishHouseRepository.deleteById(id);
    }
}

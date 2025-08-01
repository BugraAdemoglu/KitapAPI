package com.kitapkulubu.kitapapi.Repository;

import com.kitapkulubu.kitapapi.model.PublishHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishHouseRepository extends JpaRepository<PublishHouse, Long> {
}
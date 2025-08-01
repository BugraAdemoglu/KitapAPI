package com.kitapkulubu.kitapapi.Repository;

import com.kitapkulubu.kitapapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}

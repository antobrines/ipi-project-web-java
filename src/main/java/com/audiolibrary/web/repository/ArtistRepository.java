package com.audiolibrary.web.repository;

import com.audiolibrary.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    Page<Artist> findAllByNameContaining(String name, Pageable pageable);

    Boolean existsByName(String name);
    List<Artist> findAllByNameEquals(String name);
}

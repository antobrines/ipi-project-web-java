package com.audiolibrary.web.repository;

import com.audiolibrary.web.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    Boolean existsByTitle(String title);
}

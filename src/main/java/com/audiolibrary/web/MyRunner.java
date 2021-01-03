package com.audiolibrary.web;


import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public void run(String... strings) throws Exception {
        Optional<Artist> artist = artistRepository.findById(1);
        if (artist.isPresent()) {
            System.out.println("Voici le nom de l'artist avec le l'id 1 : " + artist.get().getName());
        } else {
            System.out.println("Aucun artist dispose de cet id");
        }
    }

    public static void print(Object t) {
        System.out.println(t);
    }
}


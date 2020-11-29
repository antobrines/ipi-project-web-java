package com.audiolibrary.web.controller;


import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Artist getArtistById(@PathVariable("id") Integer id){
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isPresent()) {
            return artist.get();
        }
        throw new EntityNotFoundException("L'artiste d'identifiant " + id + " n'existe pas.");
    }

}

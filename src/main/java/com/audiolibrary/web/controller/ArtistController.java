package com.audiolibrary.web.controller;


import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @RequestMapping(value = "", params = {"name"})
    public Page<Artist> findArtistWithName(
            @RequestParam("name") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection){

        return artistRepository.findAllByNameContaining(name, PageRequest.of(page, size, sortDirection, sortProperty));
    }

}

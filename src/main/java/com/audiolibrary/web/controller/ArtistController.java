package com.audiolibrary.web.controller;


import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    private void ExceptionForPagination(String name, Integer page, Integer size, Sort.Direction sortDirection, String sortProperty){
        List<String> sortPropertyList = Arrays.asList("name", "id");
        int maxPage;
        if(!sortPropertyList.contains(sortProperty)){
            throw new IllegalArgumentException("Le tri fonctionne uniquement sur 'id' et 'name'");
        }

        if (size < 2 || size > 50)  {
            throw new IllegalArgumentException("La taille des pages doit être comprise entre 1 et 50 !");
        }

        if (name != null){
            maxPage = artistRepository.findAllByNameContaining(name, PageRequest.of(
                    page,
                    size,
                    sortDirection,
                    sortProperty)
            ).getTotalPages() - 1;
        }else{
            maxPage = (int) artistRepository.count() / size - 1;
        }

        if (page < 0 || page > maxPage){
            throw new IllegalArgumentException("Le numéro de page ne peut être inférieur à 0 ou supérieur à " + maxPage);
        }
    }

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

        ExceptionForPagination(name, page, size, sortDirection, sortProperty);

        return artistRepository.findAllByNameContaining(name, PageRequest.of(page, size, sortDirection, sortProperty));
    }

    @GetMapping
    public Page<Artist> ListArtist(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection){

        ExceptionForPagination(null, page, size, sortDirection, sortProperty);

        return artistRepository.findAll(PageRequest.of(page, size, sortDirection, sortProperty));
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Artist createArtist(@RequestBody Artist artist) {
        if (artist.getName() == null) {
            throw new IllegalStateException ("L'artiste ne peut avoir un nom 'null' !");
        }

        if (artist.getName().trim().isEmpty()) {
            throw new IllegalStateException ("L'artiste ne peut avoir un nom vide !");
        }

        if(artistRepository.existsByName(artist.getName().trim())) {
            throw new DataIntegrityViolationException("L'artiste avec le nom : " + artist.getName() + ", existe déjà !");
        }

        return artistRepository.save(artist);
    }

}

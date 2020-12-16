package com.audiolibrary.web.controller;

import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Artist getArtistById(@PathVariable("id") Integer id){
        return artistService.findById(id);
    }

    @RequestMapping(value = "", params = {"name"})
    public Page<Artist> findArtistWithName(
            @RequestParam("name") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection){

        return artistService.findByName(name, page, size, sortDirection, sortProperty);
    }

    @GetMapping
    public Page<Artist> ListArtist(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection){

        return artistService.findAllArtists(page, size, sortDirection, sortProperty);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Artist updateArtist(@PathVariable("id") Integer id, @RequestBody Artist artist){
        return artistService.updateArtist(id, artist);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable ("id") Integer id){
        artistService.deleteArtist(id);
    }

}

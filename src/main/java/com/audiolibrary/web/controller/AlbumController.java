package com.audiolibrary.web.controller;

import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;


@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumRepository albumRepository;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Album createAlbum (@RequestBody Album album) {
        String titleTrim = album.getTitle().trim();
        if(albumRepository.existsByTitle(titleTrim)){
            throw new DataIntegrityViolationException("L'album "+ titleTrim + " existe déjà !");
        }

        if (album.getTitle() == null) {
            throw new IllegalStateException ("L'album ne peut avoir un titre 'null' !");
        }

        if (titleTrim.isEmpty()) {
            throw new IllegalStateException ("L'album ne peut avoir un titre vide !");
        }
        album.setTitle(titleTrim);
        return albumRepository.save(album);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum (@PathVariable("id") Integer id){
        Optional<Album> albumOptional = albumRepository.findById(id);
        if(albumOptional.isEmpty()) {
            throw new EntityNotFoundException("L'album avec l'id : " + id + ", n'existe pas !");
        }

        albumRepository.deleteById(id);
    }

}

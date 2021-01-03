package com.audiolibrary.web.controller;

import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Album createAlbum (@RequestBody Album album) {
        return albumService.createAlbum(album, null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum (@PathVariable("id") Integer id){
        albumService.deleteAlbum(id);
    }

}

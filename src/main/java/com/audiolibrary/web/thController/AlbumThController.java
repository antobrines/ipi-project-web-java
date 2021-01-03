package com.audiolibrary.web.thController;

import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.util.Optional;

@Controller
@RequestMapping(value = "/th/albums", method = RequestMethod.GET)
public class AlbumThController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @PostMapping(produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView addAlbum(@RequestParam("artistId") Integer id, Album album) {
        albumService.createAlbum(album, id);

        return new RedirectView( "/th/artists/"+ id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public RedirectView deleteAlbum(@PathVariable("id") Integer id) {
        Optional<Album> optionalAlbum = albumRepository.findById(id);
        Integer artistId = optionalAlbum.get().getArtist().getId();

        albumService.deleteAlbum(id);

        return new RedirectView("/th/artists/" + artistId);
    }
}

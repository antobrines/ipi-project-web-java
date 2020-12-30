package com.audiolibrary.web.thController;

import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.repository.ArtistRepository;
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

    @Autowired
    private ArtistRepository artistRepository;


    @PostMapping(produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView addAlbum(@RequestParam("artistId") Integer artistId, Album album) {

        Optional<Artist > optionalArtist = artistRepository.findById(artistId);
        album.setArtist(optionalArtist.get());
        Integer idArtist = optionalArtist.get().getId();
        //Search for artist albums not for all artists
        albumService.createAlbum(album, artistId);

        return new RedirectView( "/th/artists/"+ idArtist);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public RedirectView deleteAlbum(@PathVariable("id") Integer id) {

        Optional<Album> optionalAlbum = albumRepository.findById(id);

        Integer idArtist = optionalAlbum.get().getArtist().getId();

        albumService.deleteAlbum(id);

        return new RedirectView("/th/artists/" + idArtist);
    }
}

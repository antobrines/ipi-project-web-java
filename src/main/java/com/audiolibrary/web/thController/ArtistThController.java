package com.audiolibrary.web.thController;

import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/th/artists", method = RequestMethod.GET)
public class ArtistThController {

    @Autowired
    private ArtistService artistService;

    @RequestMapping(method = RequestMethod.GET, value="{id}")
    public String getArtistById(@PathVariable("id") Integer id, final ModelMap model) {
        Artist artist = artistService.findById(id);
        model.put("artist", artist);
        model.put("deleteAlbumsWithArtist", artistService.getDELETE_ALBUMS_WITH_ARTIST());
        return "detailArtist";
    }

    @GetMapping(params = {"name"})
    public String allArtistsByName(@RequestParam("name") String name,
                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                    @RequestParam(value = "size", defaultValue = "10") Integer size,
                                    @RequestParam(value = "sortProperty", defaultValue = "name") String sortProperty,
                                    @RequestParam(value = "sortDirection", defaultValue = "ASC") Sort.Direction sortDirection,
                                    final ModelMap model) {
        Page<Artist> pageA = artistService.findByName(name, page, size , sortDirection, sortProperty);
        model.put("artists", pageA);
        model.put("size", size);
        model.put("pageNumber", page);
        model.put("sortDirection", sortDirection);
        model.put("sortProperty", sortProperty);
        model.put("previousPage", page - 1);
        model.put("nextPage", page + 1);
        model.put("name", name);
        model.put("sizep", String.format("Affichage des artistes %d à %d sur un total de %d pages",
                size * page, size * (page + 1) , pageA.getTotalPages() - 1));
        return "listeArtists";
    }

    @GetMapping()
    public String allArtists(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                              @RequestParam("sortProperty") String sortProperty, @RequestParam("sortDirection") Sort.Direction sortDirection,
                              final ModelMap model) {
        Page<Artist> pageA = artistService.findAllArtists(page, size, sortDirection, sortProperty);
        model.put("artists", pageA);
        model.put("size", size);
        model.put("pageNumber", page);
        model.put("sortDirection", sortDirection);
        model.put("sortProperty", sortProperty);
        model.put("previousPage", page - 1);
        model.put("nextPage", page + 1);
        model.put("sizep",
                String.format("Affichage des artistes %d à %d sur un total de %d pages",
                        size * page,
                        size * (page + 1), pageA.getTotalPages() - 1));
        return "listeArtists";
    }

    @GetMapping(value = "/new")
    public String createArtist(final ModelMap model){
        Artist artist = new Artist();
        model.put("artist", artist);
        return "detailArtist";
    }

    @PostMapping(produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView saveArtist(final ModelMap model, Artist artist){
        Artist artistSave = artistService.createArtist(artist);
        return new RedirectView("/th/artists/" + artistSave.getId());
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    private RedirectView updateArtist(Artist artist){
        artist =  artistService.createArtist(artist);
        return new RedirectView("/th/artists/" + artist.getId());
    }



    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public RedirectView deleteArtist(@PathVariable Integer id){
        artistService.deleteArtist(id);
        return new RedirectView("/th/artists?page=0&size=10&sortProperty=name&sortDirection=ASC");
    }


    @GetMapping(produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, value = "/delete_albums_with_artist")
    public RedirectView changeDeleteAlbumsWithArtist(@RequestParam(value = "value", defaultValue = "false") Boolean value, @RequestParam("id") Integer id){
        artistService.changeDeleteAlbumsWithArtist(value);
        return new RedirectView("/th/artists/"+id);
    }
}

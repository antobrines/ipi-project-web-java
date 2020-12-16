package com.audiolibrary.web.thController;

import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/th/artists", method = RequestMethod.GET)
public class ArtistThController {

    @Autowired
    private ArtistService artistService;

    @RequestMapping(method = RequestMethod.GET, value="{id}")
    public String getArtistById(@PathVariable("id") Integer id, final ModelMap model) {
        Artist artist = artistService.findById(id);
        model.put("artist", artist);
        return "detailArtist";
    }
}

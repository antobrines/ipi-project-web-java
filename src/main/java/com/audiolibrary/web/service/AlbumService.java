package com.audiolibrary.web.service;

import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public Album createAlbum(Album album, Integer artistId) {
        String titleTrim = album.getTitle().trim();
        if (artistId != null){
            Optional<Artist> optionalArtist = artistRepository.findById(artistId);
            List<Album> albums = optionalArtist.get().getAlbums();
            for(Album albumC : albums){
                if(albumC.getTitle().trim().equals(titleTrim)){
                    throw new DataIntegrityViolationException("L'album "+ titleTrim + " existe déjà dans"+ artistRepository.findById(artistId).get().getName());
                }
            }
        }else if(albumRepository.existsByTitle(titleTrim)){
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




    public void deleteAlbum(Integer id){
        Optional<Album> albumOptional = albumRepository.findById(id);
        if(albumOptional.isEmpty()) {
            throw new EntityNotFoundException("L'album avec l'id : " + id + ", n'existe pas !");
        }

        albumRepository.deleteById(id);
    }


}

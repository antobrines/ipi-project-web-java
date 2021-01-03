package com.audiolibrary.web.service;

import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.AlbumRepository;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.security.Signature;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Value("${deleteAlbumsWithArtist}")
    private Boolean DELETE_ALBUMS_WITH_ARTIST;

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
            if(page < 0){
                throw new IllegalArgumentException("Le numéro de page ne peut être inférieur à 0");
            }
            maxPage = artistRepository.findAllByNameContaining(name, PageRequest.of(
                    page,
                    size,
                    sortDirection,
                    sortProperty)
            ).getTotalPages() - 1;
        }else{
            maxPage = (int) artistRepository.count() / size;
        }



        if (page < 0 || page > maxPage && maxPage != -1){
            System.out.println(page);
            System.out.println(maxPage);
            throw new IllegalArgumentException("Le numéro de page ne peut être inférieur à 0 ou supérieur à " + maxPage);
        }
    }

    public Artist findById(Integer id){
        Optional<Artist> artist = artistRepository.findById(id);
        if(artist.isPresent()) {
            return artist.get();
        }
        throw new EntityNotFoundException("L'artiste d'identifiant " + id + " n'existe pas.");
    }

    public Page<Artist> findByName(String name, Integer page, Integer size, Sort.Direction sortDirection, String sortProperty) {

        ExceptionForPagination(name, page, size, sortDirection, sortProperty);

        return artistRepository.findAllByNameContaining(name, PageRequest.of(page, size, sortDirection, sortProperty));
    }

    public Page<Artist> findAllArtists(Integer page, Integer size, Sort.Direction sortDirection, String sortProperty) {

        ExceptionForPagination(null, page, size, sortDirection, sortProperty);

        return artistRepository.findAll(PageRequest.of(page, size, sortDirection, sortProperty));
    }

    public Artist createArtist(Artist artist) {
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

    public Artist updateArtist(Integer id, Artist artist){
        if (artist.getName().trim().isEmpty()) {
            throw new IllegalStateException ("L'artiste doit avoir un nom !");
        }

        if(!artistRepository.existsById(id)) {
            throw new EntityNotFoundException("L'artiste avec l'id : " + id + ", n'existe pas !");
        }

        if(!id.equals(artist.getId())) {
            throw new IllegalArgumentException("L'id ne correspond pas à celui de cet artiste");
        }

        if(artistRepository.existsByName(artist.getName().trim())){
            throw new DataIntegrityViolationException("L'artiste existe déjà !");
        }

        return artistRepository.save(artist);
    }

    public void deleteArtist(Integer id){
        Optional<Artist> artistOptional = artistRepository.findById(id);

        if(artistOptional.isEmpty()) {
            throw new EntityNotFoundException("L'artiste avec l'id : " + id + ", n'existe pas !");
        }

        Artist artist = artistOptional.get();

        if(!artist.getAlbums().isEmpty()) {
            if (DELETE_ALBUMS_WITH_ARTIST) {
                List<Album> albums = artist.getAlbums();
                //CASCADE ?
                for (Album album : albums) {
                    albumRepository.delete(album);
                }
            }else {
                throw new DataIntegrityViolationException("Cet artiste dispose d'albums, vous ne pouvez pas le supprimer !");
            }
        }
        artistRepository.deleteById(id);
    }

    public Boolean getDELETE_ALBUMS_WITH_ARTIST(){
        return DELETE_ALBUMS_WITH_ARTIST;
    }

    public void changeDeleteAlbumsWithArtist(Boolean value){
        System.out.println(value);
        DELETE_ALBUMS_WITH_ARTIST = value;
    }
}

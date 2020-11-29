package com.audiolibrary.web.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AlbumId")
    private Integer id;

    @Column(name = "Title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "ArtistId")
    private Artist artist;

    public Album() {}

    public Album(Integer id, String title, Artist artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Album{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", artist=").append(artist);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return Objects.equals(getId(), album.getId()) &&
                Objects.equals(getTitle(), album.getTitle()) &&
                Objects.equals(getArtist(), album.getArtist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getArtist());
    }
}

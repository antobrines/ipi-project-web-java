package com.audiolibrary.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ArtistId")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "artist")
    @JsonIgnoreProperties("artist")
    private List<Album> albums;

    public Artist(){}

    public Artist(Integer id, String name, List<Album> albums) {
        this.id = id;
        this.name = name;
        this.albums = albums;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Artist{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", albums=").append(albums);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(getId(), artist.getId()) &&
                Objects.equals(getName(), artist.getName()) &&
                Objects.equals(getAlbums(), artist.getAlbums());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAlbums());
    }
}

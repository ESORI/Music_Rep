package cat.uvic.teknos.musicrep.domain.jpa.models;
import com.esori.list.models.Artist;
import com.esori.list.models.Song;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ALBUM")
public class Album implements com.esori.list.models.Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_ALBUM")
    private int id;

    @Column(name="ALBUM_NAME")
    private String albumName;
    @Column(name="QUANTITY_SONGS")
    private int nSongs;
    @ManyToOne(targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Artist.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ARTIST")
    private Artist artist;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Song.class)
    @JoinColumn(name = "ID_ALBUM")
    private Set<Song> song = new HashSet<>();



    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getAlbumName() {
        return albumName;
    }

    @Override
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public int getNSongs() {
        return nSongs;
    }

    @Override
    public void setNSongs(int nSongs) {
        this.nSongs = nSongs;
    }

    @Override
    public Artist getArtist() {
        return artist;
    }

    @Override
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public Set<Song> getSong() {
        return song;
    }

    @Override
    public void setSong(Set<Song> song) {
        this.song = song;
    }
}


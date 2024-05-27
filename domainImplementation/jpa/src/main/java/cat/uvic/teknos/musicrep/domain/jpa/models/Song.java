package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Album;
import com.esori.list.models.Artist;
import com.esori.list.models.Playlist;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="SONG")
public class Song implements com.esori.list.models.Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SONG")
    private int id;
    @Column(name = "SONG_NAME")
    private String songName;
    @Column(name = "DURATION")
    private int duration;
    @ManyToOne(targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Artist.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ARTIST")
    private Artist artist;
    @ManyToOne(targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Album.class, fetch = FetchType.LAZY)
    @JoinColumn(name="ID_ALBUM")
    private Album album;

    @ManyToMany(
            mappedBy = "songs",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Playlist.class)
    private Set<Playlist> playlists = new HashSet<>();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getSongName() {
        return songName;
    }

    @Override
    public void setSongName(String songName) {
        this.songName=songName;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration=duration;
    }

    @Override
    public Artist getArtist() {
        return artist;
    }

    @Override
    public void setArtist(Artist artist) {
        this.artist=artist;
    }

    @Override
    public Album getAlbum() {
        return album;
    }

    @Override
    public void setAlbum(Album album) {
        this.album=album;
    }

    @Override
    public Set<Playlist> getPlaylist() {
        return playlists;
    }

    @Override
    public void setPlaylist(Set<Playlist> playlist) {
        this.playlists=playlist;
    }
}




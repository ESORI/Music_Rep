package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Album;
import com.esori.list.models.Artist;
import com.esori.list.models.Playlist;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="SONGS")
public class Song implements com.esori.list.models.Song {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(name = "SONG NAME")
    private String songName;
    @Column(name = "DURATION")
    private int duration;
    @Transient
    private Artist artist;
    @Transient
    private Album album;
    @Transient
    private Set<Playlist> playlist;

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
        return playlist;
    }

    @Override
    public void setPlaylist(Set<Playlist> playlist) {
        this.playlist=playlist;
    }
}

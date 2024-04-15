package cat.uvic.teknos.models;
import com.esori.list.models.Artist;
import com.esori.list.models.Song;

import java.io.Serializable;
import java.util.Set;

public class Album implements com.esori.list.models.Album, Serializable {

    private int id;
    private String albumName;
    private int nSongs;
    private Artist artist;
    private Set<Song> song;


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

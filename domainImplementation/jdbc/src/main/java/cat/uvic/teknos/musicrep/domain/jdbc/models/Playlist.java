package cat.uvic.teknos.musicrep.domain.jdbc.models;

import com.esori.list.models.Song;
import com.esori.list.models.User;

import java.util.Set;

public class Playlist implements com.esori.list.models.Playlist {

    private int id;
    private int nSongs;
    private String playlistName;
    private String description;
    private int duration;
    private Set<Song> song;
    private Set<User> user;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id=id;
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
    public String getPlaylistName() {
        return playlistName;
    }

    @Override
    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description=description;
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
    public Set<Song> getSong() {
        return song;
    }

    @Override
    public void setSong(Set<Song> song) {
        this.song = song;
    }

    @Override
    public Set<User> getUser() {
        return user;
    }

    @Override
    public void setUser(Set<User> user) {
        this.user = user;
    }
}

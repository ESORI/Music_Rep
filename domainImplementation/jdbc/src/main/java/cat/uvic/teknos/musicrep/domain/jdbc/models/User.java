package cat.uvic.teknos.musicrep.domain.jdbc.models;

import com.esori.list.models.Playlist;

import java.util.Set;

public class User implements com.esori.list.models.User {

    private int id;
    private String username;
    private Set<Playlist> playlist;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username=username;
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
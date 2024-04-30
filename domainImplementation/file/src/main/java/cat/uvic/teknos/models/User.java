package cat.uvic.teknos.models;

import com.esori.list.models.Playlist;
import com.esori.list.models.UserData;

import java.io.Serializable;
import java.util.Set;

public class User implements com.esori.list.models.User, Serializable {

    private int id;
    private String username;
    private Set<Playlist> playlist;
    private UserData userData;

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

    @Override
    public UserData getUserData() {
        return userData;
    }

    @Override
    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}

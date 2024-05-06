package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Playlist;
import com.esori.list.models.UserData;
import jakarta.persistence.*;

import java.util.Set;
//CHECK USER DATA
@Entity
@Table(name="USERS")
public class User implements com.esori.list.models.User {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(name = "USERNAME")
    private String username;
    @Transient
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

    @Override
    public UserData getUserData() {
        return null;
    }

    @Override
    public void setUserData(UserData userData) {

    }
}

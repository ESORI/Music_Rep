package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Playlist;
import com.esori.list.models.UserData;
import jakarta.persistence.*;

import java.util.Set;
//CHECK USER DATA
@Entity
@Table(name="USER")
public class User implements com.esori.list.models.User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_USER")
    private int id;

    @Column(name="USERNAME")
    private String username;

    @ManyToMany(
            mappedBy = "users",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Playlist.class)
    private Set<Playlist> playlists;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.UserData.class)
    @PrimaryKeyJoinColumn
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
        return playlists;
    }

    @Override
    public void setPlaylist(Set<Playlist> playlist) {
        this.playlists=playlist;
    }

    @Override
    public UserData getUserData() {
        return userData;
    }

    @Override
    public void setUserData(UserData userData) {
        this.userData=userData;
        this.userData.setUser(this);
    }
}

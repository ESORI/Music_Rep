package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Song;
import com.esori.list.models.User;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name="PLAYLIST")
public class Playlist implements com.esori.list.models.Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PLAYLIST")
    private int id;
    @Column(name = "QUANTITY_SONGS")
    private int nSongs;
    @Column(name = "PLAYLIST_NAME")
    private String playlistName;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DURATION")
    private int duration;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Song.class)
    @JoinTable(name = "PLAYLIST_SONG",
            joinColumns = {@JoinColumn(name="ID_PLAYLIST")},
            inverseJoinColumns = {@JoinColumn(name = "ID_SONG")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"ID_PLAYLIST", "ID_SONG"})})
    private Set<Song> songs = new HashSet<>();

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.User.class)
    @JoinTable(name = "PLAYLIST_USER",
            joinColumns = {@JoinColumn(name="ID_PLAYLIST")},
            inverseJoinColumns = {@JoinColumn(name = "ID_USER")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"ID_PLAYLIST", "ID_USER"})})
    private Set<User> users;

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
        return songs;
    }

    @Override
    public void setSong(Set<Song> song) {
        this.songs = song;
    }

    @Override
    public Set<User> getUser() {
        return users;
    }

    @Override
    public void setUser(Set<User> user) {
        this.users = user;
    }
}


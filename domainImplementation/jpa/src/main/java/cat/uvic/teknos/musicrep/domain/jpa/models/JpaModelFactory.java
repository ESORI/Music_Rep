package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Artist;
import com.esori.list.models.Playlist;
import com.esori.list.models.Song;
import com.esori.list.models.User;
import com.esori.list.models.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.Properties;

public class JpaModelFactory implements ModelFactory {


    @Override
    public User createUser() {
        return new cat.uvic.teknos.musicrep.domain.jpa.models.User();
    }

    @Override
    public Artist createArtist() {
        return new cat.uvic.teknos.musicrep.domain.jpa.models.Artist();
    }

    @Override
    public Playlist createPlaylist() {
        return new cat.uvic.teknos.musicrep.domain.jpa.models.Playlist();
    }
}

package cat.uvic.teknos.musicrep.domain.jdbc.models;

import com.esori.list.models.Artist;
import com.esori.list.models.ModelFactory;
import com.esori.list.models.Playlist;
import com.esori.list.models.Song;
import com.esori.list.models.User;

public class JdbcModelFactory implements ModelFactory {



    @Override
    public User createUser() {
        return new cat.uvic.teknos.musicrep.domain.jdbc.models.User();
    }

    @Override
    public Artist createArtist() {
        return new cat.uvic.teknos.musicrep.domain.jdbc.models.Artist();
    }

    @Override
    public Playlist createPlaylist() {
        return new cat.uvic.teknos.musicrep.domain.jdbc.models.Playlist();
    }
}

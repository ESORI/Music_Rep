package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Playlist;
import com.esori.list.repositories.PlaylistRepository;

import java.sql.Connection;
import java.util.Set;


//THIS REPOSITORY WILL HANDLE PLAYLIST, PLAYLIST_USER AND PLAYLIST_SONG
//DEPENDING ON HOW THE OTHER REPOSITORIES WORK THIS ONE WILL HAVE INSERT AND UPDATE OR A SIMPLE INSERT WITH DUPLICATE ON KEY UPDATE

public class JdbcPlaylistRepository implements PlaylistRepository {

    private static final String INSERT_PLAYLIST = "INSERT INTO PLAYLIST (QUANTITY_SONGS, PLAYLIST_NAME,DESCRIPTION,DURATION) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE";
    private static final String INSERT_PLAYLIST_USER = "INSERT INTO PLAYLIST_USER (ID_PLAYLIST, ID_USER) VALUES (?,?) ON DUPLICATE KEY UPDATE";
    private static final String INSERT_PLAYLIST_SONG = "INSERT INTO PLAYLIST_SONG (ID_PLAYLIST, ID_SONG) VALUES (?,?) ON DUPLICATE KEY UPDATE";

    private final Connection connection;

    public JdbcPlaylistRepository(Connection connection){
        this.connection = connection;
    }


    @Override
    public void save(Playlist model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(Playlist model){

    }

    private void update(Playlist model){

    }

    @Override
    public void delete(Playlist model) {

    }

    @Override
    public Playlist get(Integer id) {
        return null;
    }

    @Override
    public Set<Playlist> getAll() {
        return null;
    }
}

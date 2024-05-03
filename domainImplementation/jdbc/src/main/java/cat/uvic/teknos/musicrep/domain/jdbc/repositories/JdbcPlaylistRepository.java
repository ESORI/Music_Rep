package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Playlist;
import com.esori.list.repositories.PlaylistRepository;

import java.sql.Connection;
import java.util.Set;


//THIS REPOSITORY WILL HANDLE PLAYLIST

public class JdbcPlaylistRepository implements PlaylistRepository {

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

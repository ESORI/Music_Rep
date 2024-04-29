package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Album;
import com.esori.list.repositories.AlbumRepository;

import java.sql.Connection;
import java.util.Set;

public class JdbcAlbumRepository implements AlbumRepository {

    private final Connection connection;

    public JdbcAlbumRepository(Connection connection){
        this.connection = connection;
    }


    @Override
    public void save(Album model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(Album model){

    }

    private void update(Album model){

    }

    @Override
    public void delete(Album model) {

    }

    @Override
    public Album get(Integer id) {
        return null;
    }

    @Override
    public Set<Album> getAll() {
        return null;
    }
}

package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.ArtistData;
import com.esori.list.repositories.ArtistDataRepository;

import java.sql.Connection;
import java.util.Set;

public class JdbcArtistDataRepository implements ArtistDataRepository {

    private final Connection connection;

    public JdbcArtistDataRepository(Connection connection){
        this.connection = connection;
    }


    @Override
    public void save(ArtistData model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(ArtistData model){

    }

    private void update(ArtistData model){

    }

    @Override
    public void delete(ArtistData model) {

    }

    @Override
    public ArtistData get(Integer id) {
        return null;
    }

    @Override
    public Set<ArtistData> getAll() {
        return null;
    }
}

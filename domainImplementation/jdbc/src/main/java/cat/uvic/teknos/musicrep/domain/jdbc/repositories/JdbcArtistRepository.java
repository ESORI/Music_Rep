package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Artist;
import com.esori.list.repositories.ArtistRepository;

import java.util.Set;

public class JdbcArtistRepository implements ArtistRepository {
    @Override
    public void save(Artist model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(Artist model){

    }

    private void update(Artist model){

    }

    @Override
    public void delete(Artist model) {

    }

    @Override
    public Artist get(Integer id) {
        return null;
    }

    @Override
    public Set<Artist> getAll() {
        return null;
    }
}

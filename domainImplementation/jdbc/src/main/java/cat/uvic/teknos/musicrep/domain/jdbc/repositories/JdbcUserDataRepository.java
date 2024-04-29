package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.UserData;
import com.esori.list.repositories.UserDataRepository;

import java.sql.Connection;
import java.util.Set;

public class JdbcUserDataRepository implements UserDataRepository {

    private final Connection connection;

    public JdbcUserDataRepository(Connection connection){
        this.connection = connection;
    }


    @Override
    public void save(UserData model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(UserData model){

    }

    private void update(UserData model){

    }

    @Override
    public void delete(UserData model) {

    }

    @Override
    public UserData get(Integer id) {
        return null;
    }

    @Override
    public Set<UserData> getAll() {
        return null;
    }
}

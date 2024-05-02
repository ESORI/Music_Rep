package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.User;
import com.esori.list.repositories.UserRepository;

import java.sql.Connection;
import java.util.Set;


//THIS REPOSITORY WILL HANDLE USER AND USERDATA
public class JdbcUserRepository implements UserRepository {

    private final Connection connection;

    public JdbcUserRepository(Connection connection){
        this.connection = connection;
    }


    @Override
    public void save(User model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(User model){

    }

    private void update(User model){

    }

    @Override
    public void delete(User model) {

    }

    @Override
    public User get(Integer id) {
        return null;
    }

    @Override
    public Set<User> getAll() {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }
}

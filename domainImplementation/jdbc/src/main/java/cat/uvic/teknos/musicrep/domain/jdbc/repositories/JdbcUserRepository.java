package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.UserData;
import com.esori.list.models.User;

import com.esori.list.repositories.UserRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


//THIS REPOSITORY WILL HANDLE USER AND USERDATA
//NEEDS TO UPDATE USER AND USERDATA MODELS ON JDBC
public class JdbcUserRepository implements UserRepository {

    private static final String INSERT_USER = "INSERT INTO USER (USERNAME) VALUES (?) ";
    private static final String INSERT_USER_DATA = "INSERT INTO USER_DATA VALUES (?,?,?,?,?) ";

    private final Connection connection;

    public JdbcUserRepository(Connection connection){
        this.connection = connection;
    }


    //make saveUserData

    @Override
    public void save(User model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(User model){
        try(
                var preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                var userDataStatement = connection.prepareStatement(INSERT_USER_DATA)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, model.getUsername());

            preparedStatement.executeUpdate();
            var keys = preparedStatement.getGeneratedKeys();
            if(keys.next()){
                model.setId(keys.getInt(1));
            }
            if(model.getUserData()!=null){
                //saveUserData(model, id);
                userDataStatement.setInt(1, model.getId());
                userDataStatement.setString(2,model.getUserData().getUserName());
                userDataStatement.setInt(3,model.getUserData().getPhoneNumber());
                userDataStatement.setString(4,model.getUserData().getCountry());
                userDataStatement.setInt(5,model.getUserData().getAge());

            }
            connection.commit();
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally{
            setAutocommitFalse();
        }

    }

    private void update(User model){
        try(
                var preparedStatement = connection.prepareStatement("UPDATE USER SET USERNAME = (?) WHERE ID_USER = (?)");
                var userDataStatement = connection.prepareStatement("UPDATE USER_DATA SET USER_NAME = (?) WHERE ID_USER = (?)")){
            connection.setAutoCommit(false);

            //Will not update User unless there's data, but will still check for UserData
            if(model.getUsername()!=null){
                preparedStatement.setString(1, model.getUsername());
                preparedStatement.setInt(2, model.getId());
                preparedStatement.executeUpdate();
            }


            if(model.getUserData()!=null){
                userDataStatement.setInt(2, model.getId());
                userDataStatement.setString(1,model.getUserData().getUserName());
            }
            connection.commit();
        }catch(SQLException e){
            rollback();
            throw new RuntimeException(e);
        }finally{
            setAutocommitFalse();
        }

    }

    @Override
    public void delete(User model) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER where ID_USER = ?");
            PreparedStatement userDataStatement = connection.prepareStatement("DELETE FROM USER_DATA where ID_USER = ?")) {

            connection.setAutoCommit(false);
            preparedStatement.setInt(1,model.getId());

            userDataStatement.setInt(1,model.getId());
            userDataStatement.executeUpdate();

            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutocommitFalse();
        }

    }

    @Override
    public User get(Integer id) {
        String query = "SELECT * FROM USER WHERE ID_USER = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User result = new cat.uvic.teknos.musicrep.domain.jdbc.models.User();
                    result.setId(resultSet.getInt("ID_USER"));
                    result.setUsername(resultSet.getString("USERNAME"));

                    UserData userData = new UserData();
                    userData.setUserName(resultSet.getString("USER_NAME"));
                    result.setUserData(userData);


                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Set<User> getAll() {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USER")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<User> users = new HashSet<>();
            while (resultSet.next()) {
                User user = new cat.uvic.teknos.musicrep.domain.jdbc.models.User();
                user.setId(resultSet.getInt("ID_USER"));
                user.setUsername(resultSet.getString("USERNAME"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

}

    @Override
    public User getByUsername(String username) {
        String query = "SELECT * FROM USER WHERE USERNAME = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User result = new cat.uvic.teknos.musicrep.domain.jdbc.models.User();
                    result.setId(resultSet.getInt("ID_USER"));
                    result.setUsername(resultSet.getString("USERNAME"));

                    UserData userData = new UserData();
                    userData.setUserName(resultSet.getString("USER_NAME"));
                    result.setUserData(userData);


                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private void setAutocommitFalse() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}

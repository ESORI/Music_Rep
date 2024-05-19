package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.User;
import cat.uvic.teknos.musicrep.domain.jdbc.models.UserData;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcUserRepositoryTest {

    private final Connection connection;

    public JdbcUserRepositoryTest(Connection connection){
        this.connection = connection;
    }


    @Test
    @DisplayName("Given a new genre (id = 0), when save, then a new record is added to the USER (and maybe USER_DATA) table")
    void shouldInsertNewUser() {
        User user = new User();
        user.setUsername("testing123");

        UserData userData = new UserData();
        userData.setUserName("Rodri");
        userData.setAge(27);
        userData.setCountry("RO");
        userData.setPhoneNumber(666666666);

        user.setUserData(userData);

        var repository = new JdbcUserRepository(connection);

        // Test
        repository.save(user);

        assertTrue(user.getId() > 0);


        DbAssertions.assertThat(connection)
                .table("USER")
                .where("ID_USER", user.getId())
                .hasOneLine();
    }

    @Test
    void shouldUpdateUser(){

        //updates all
        User user = new User();
        user.setId(3);
        user.setUsername("kylXr");

        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setUserName("Javier");
        userData.setAge(33);
        userData.setCountry("LI");
        userData.setPhoneNumber(888555222);

        user.setUserData(userData);

        //updates just user

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("AsomBroso");

        //updates just userData -> user name
        User user2 = new User();
        user2.setId(2);

        UserData userData1 = new UserData();
        userData1.setId(user2.getId());
        userData1.setUserName("Erika");
        userData1.setAge(28);
        userData1.setCountry("RO");
        userData1.setPhoneNumber(111444777);

        user2.setUserData(userData1);

        var repository = new JdbcUserRepository(connection);
        repository.save(user);
        repository.save(user1);
        repository.save(user2);
    }

    @Test
    void delete() {
        User user = new User();
        user.setId(1);

        var repository = new JdbcUserRepository(connection);
        repository.delete(user);

        DbAssertions.assertThat(connection)
                .table("USER")
                .where("ID_USER", user.getId())
                .doesNotExist();
    }

    @Test
    void get() {
        int id = 3;
        var repository = new JdbcUserRepository(connection);
        assertNotNull(repository.get(id));

        com.esori.list.models.User user = repository.get(id);
        SoutUser(user);

    }



    @Test
    void getAll() {
        var repository = new JdbcUserRepository(connection);

        Set<com.esori.list.models.User> users = repository.getAll();
        assertNotNull(users);

        for(var user:users){
            SoutUser(user);
        }

    }

    @Test
    void getByUsername() {
        String id = "Bombis";
        var repository = new JdbcUserRepository(connection);
        assertNotNull(repository.getByUsername(id));

        com.esori.list.models.User user = repository.getByUsername(id);
        SoutUser(user);
    }

    private static void SoutUser(com.esori.list.models.User user) {
        System.out.println("User with id: " + user.getId());
        System.out.println("Username:" +  user.getUsername());
        if(user.getUserData()!=null){
            System.out.println("-----\nData from User");
            System.out.println("Name: " + user.getUserData().getUserName());
            System.out.println("Phone number: " + user.getUserData().getPhoneNumber());
            System.out.println("Country: " + user.getUserData().getCountry());
            System.out.println("Age: " + user.getUserData().getAge());
        }else{
            System.out.println("-----\nData from User not available");
        }
        System.out.println("\n\n");
    }
}
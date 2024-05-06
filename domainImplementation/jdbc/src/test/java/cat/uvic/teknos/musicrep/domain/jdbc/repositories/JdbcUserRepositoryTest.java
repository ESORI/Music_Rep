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
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByUsername() {
    }


}
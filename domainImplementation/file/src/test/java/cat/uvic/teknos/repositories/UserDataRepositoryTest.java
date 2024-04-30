package cat.uvic.teknos.repositories;


import cat.uvic.teknos.models.UserData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class UserDataRepositoryTest {

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/usersData.ser";

        var repository = new UserDataRepository(dataPath);

        var userD = new UserData();
        userD.setAge(27);
        userD.setUserName("Marco");
        userD.setCountry("PT");
        userD.setPhoneNumber(666666666);

        repository.save(userD);

        assertTrue(userD.getId()>0);
        assertNotNull(repository.get(userD.getId()));
        assertNotNull(repository.get(userD.getId()).getUserName());

        repository.load();
        assertNotNull(repository.get(userD.getId()));
        assertEquals("Marco", repository.get(userD.getId()).getUserName());
    }

    @Test
    void updateUser() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersDataUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testUsersDataUpdate.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new UserDataRepository(testDataPath);

        var userD = new UserData();
        userD.setId(1);
        userD.setAge(28);
        userD.setUserName("Laura");
        userD.setCountry("ESP");
        userD.setPhoneNumber(636363636);

        repository.save(userD);

        var updatedUser = repository.get(1);
        assertEquals(1, updatedUser.getId());

        repository.load();
        var updatedUserFromFile = repository.get(1);
        assertEquals(1, updatedUserFromFile.getId());

    }

    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersDataDelete.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testUsersDataDelete.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new UserDataRepository(testDataPath);

        var user = new UserData();
        user.setId(1);
        user.setUserName("Alvaro");


        repository.save(user);
        assertNotNull(repository.get(1));

        repository.delete(user);

        assertNull(repository.get(1));
    }

    @Test
    void get() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersDataGet.ser";

        var repository = new UserDataRepository(dataPath);

        var user = new UserData();
        user.setUserName("Rodrigo");
        repository.save(user);


        assertNotNull(repository.get(user.getId()));
    }

    @Test
    void getAll() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersDataGetAll.ser";

        var repository = new UserDataRepository(dataPath);

        repository.getAll().forEach(repository::delete);

        var user = new UserData();
        user.setUserName("Clara");
        repository.save(user);

        var user2 = new UserData();
        user2.setUserName("Angel");
        repository.save(user2);

        var user3 = new UserData();
        user3.setUserName("Minerva");
        repository.save(user3);

        repository.getAll();
        assertEquals(3, repository.getAll().size());
    }


}
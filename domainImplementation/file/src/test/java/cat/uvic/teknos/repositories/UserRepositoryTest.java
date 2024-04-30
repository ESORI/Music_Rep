package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.Playlist;
import cat.uvic.teknos.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {



    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/users.ser";

        var repository = new UserRepository(dataPath);

        //Create and save new user with some of its attributes
        var playlist = new Playlist();
        playlist.setId(1);
        playlist.setPlaylistName("Sleepy time");
        playlist.setDescription("Songs to sleep to");

        var playlists = new HashSet<com.esori.list.models.Playlist>();
        playlists.add(playlist);

        var user = new User();
        user.setUsername("user111");
        user.setPlaylist(playlists);

        repository.save(user);

        //check if the values exist after creating and adding it to the repository
        assertTrue(user.getId()>0);
        assertNotNull(repository.get(user.getId()));
        assertNotNull(repository.get(user.getId()).getUsername());

        repository.load();
        assertNotNull(repository.get(user.getId()));
        assertEquals("user111", repository.get(user.getId()).getUsername());
    }

    @Test
    void updateUser() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testUsersUpdate.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new UserRepository(testDataPath);

        //since we're updating we create a user over an already existing id and follow the same steps as save()
        var playlist = new Playlist();
        playlist.setId(1);
        playlist.setPlaylistName("Sleepy time");
        playlist.setDescription("Songs to sleep to");

        var playlists = new HashSet<com.esori.list.models.Playlist>();
        playlists.add(playlist);

        var user = new User();
        user.setId(1);
        user.setUsername("user222");
        user.setPlaylist(playlists);

        repository.save(user);

        var updatedUser = repository.get(1);
        assertEquals(1, updatedUser.getId());

        repository.load();
        var updatedUserFromFile = repository.get(1);
        assertEquals(1, updatedUserFromFile.getId());



    }

    @Test
    void updateUserThatDoesNotExist() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testUsersUpdate.ser";

        int id = 5;

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new UserRepository(testDataPath);


        //Following the same logic as the previous test, we write over a user that doesn't exist (that id does not belong to anyone)
        var user = new User();
        user.setId(id);
        user.setUsername("user222");


        //Since we did a throw new Exception in the repository the moment an id is not found when updating, we do an assertThrows for the method
        assertThrows(RuntimeException.class, () -> repository.save(user));
        }

    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersDelete.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testUsersDelete.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new UserRepository(testDataPath);

        var user = new User();
        user.setId(1);
        user.setUsername("u222");

        //Finds user and proceeds to erase (write as null) every attribute

        repository.save(user);
        assertNotNull(repository.get(1));

        repository.delete(user);

        assertNull(repository.get(1));

    }

    @Test
    void get() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersGet.ser";

        var repository = new UserRepository(dataPath);

        var user = new User();
        user.setUsername("us123");
        repository.save(user);

        assertNotNull(repository.get(user.getId()));

    }

    @Test
    void getAll() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersGetAll.ser";

        var repository = new UserRepository(dataPath);


        //Since we're assertEquals later equal to the number of users we're adding currently, it would only
        //run errors since it would be searching for the users added now along with the ones we added before
        //Meaning we have to delete the repository first so no (size) error pops up
        repository.getAll().forEach(repository::delete);

        var user = new User();
        user.setUsername("us123");
        repository.save(user);

        var user2 = new User();
        user2.setUsername("us456");
        repository.save(user2);

        var user3 = new User();
        user3.setUsername("us789");
        repository.save(user3);

        repository.getAll();
        assertEquals(3, repository.getAll().size());

    }

    @Test
    void getByUsername(){
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/usersGetByUsername.ser";

        var repository = new UserRepository(dataPath);

        var user = new User();
        user.setUsername("user1999");
        repository.save(user);


        assertNotNull(repository.getByUsername(user.getUsername()));
        repository.getByUsername(user.getUsername());
    }

}
package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.Playlist;
import cat.uvic.teknos.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/users.ser";

        var repository = new UserRepository(dataPath);

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

        assertTrue(user.getId()>0);
        assertNotNull(repository.get(user.getId()));

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


        var user = new User();
        user.setId(id);
        user.setUsername("user222");


        assertThrows(RuntimeException.class, () -> repository.save(user));
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
}
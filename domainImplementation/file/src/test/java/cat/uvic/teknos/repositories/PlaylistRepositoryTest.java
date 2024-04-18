package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.Playlist;
import cat.uvic.teknos.models.Song;
import cat.uvic.teknos.models.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistRepositoryTest {

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/playlists.ser";

        var repository = new PlaylistRepository(dataPath);

        var user = new User();
        user.setUsername("u123");
        user.setId(1);

        var users = new HashSet<com.esori.list.models.User>();
        users.add(user);

        var pl = new Playlist();
        pl.setPlaylistName("Sleepy time");
        pl.setDescription("Songs to sleep to");
        pl.setNSongs(12);
        pl.setUser(users);

        repository.save(pl);

        assertTrue(pl.getId()>0);
        assertNotNull(repository.get(pl.getId()));
        assertNotNull(repository.get(pl.getId()).getDescription());

        repository.load();
        assertNotNull(repository.get(pl.getId()));
        assertEquals(12, repository.get(pl.getId()).getNSongs());
    }

    @Test
    void updatePlaylist() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/playlistsUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testPlaylistsUpdate.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new PlaylistRepository(testDataPath);

        var user = new User();
        user.setUsername("unknown");
        user.setId(1);

        var users = new HashSet<com.esori.list.models.User>();
        users.add(user);

        var pl = new Playlist();
        pl.setId(1);
        pl.setPlaylistName("Work work");
        pl.setDescription("Songs to start doing some sport");
        pl.setNSongs(17);
        pl.setUser(users);

        repository.save(pl);

        var updatedPlaylist = repository.get(1);
        assertEquals(1, updatedPlaylist.getId());

        repository.load();
        var updatedUserFromFile = repository.get(1);
        assertEquals(1, updatedUserFromFile.getId());

    }
    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/playlistsDelete.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testPlaylistsDelete.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new PlaylistRepository(testDataPath);

        var pl = new Playlist();
        pl.setId(1);
        pl.setPlaylistName("Work work");

        repository.save(pl);
        assertNotNull(repository.get(1));

        repository.delete(pl);

        assertNull(repository.get(1));
    }

    @Test
    void get() {

        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/playlistsGet.ser";

        var repository = new PlaylistRepository(dataPath);

        var pl = new Playlist();

        pl.setPlaylistName("Bathing");
        repository.save(pl);

        assertNotNull(repository.get(pl.getId()));
    }

    @Test
    void getAll() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/playlistsGetAll.ser";

        var repository = new PlaylistRepository(dataPath);

        repository.getAll().forEach(repository::delete);

        var pl = new Playlist();
        pl.setPlaylistName("Bathing");
        repository.save(pl);

        var pl2 = new Playlist();
        pl2.setPlaylistName("Gaming");
        repository.save(pl2);

        var pl3 = new Playlist();
        pl3.setPlaylistName("Working out");
        repository.save(pl3);

        repository.getAll();
        assertEquals(3, repository.getAll().size());
    }
}
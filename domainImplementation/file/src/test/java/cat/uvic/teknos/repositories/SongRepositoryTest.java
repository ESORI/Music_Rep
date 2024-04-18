package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.Playlist;
import cat.uvic.teknos.models.Song;
import cat.uvic.teknos.models.User;
import cat.uvic.teknos.models.UserData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SongRepositoryTest {

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/songs.ser";

        var repository = new SongRepository(dataPath);

        var song = new Song();
        song.setSongName("Faint");
        song.setDuration(207);

        repository.save(song);

        assertTrue(song.getId()>0);
        assertNotNull(repository.get(song.getId()));
        assertNotNull(repository.get(song.getId()).getSongName());

        repository.load();
        assertNotNull(repository.get(song.getId()));
        assertEquals("Faint", repository.get(song.getId()).getSongName());
    }

    @Test
    void updateSong() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/songsUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testSongsUpdate.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new SongRepository(testDataPath);

        var song = new Song();
        song.setSongName("Numb");
        song.setDuration(207);

        repository.save(song);

        var updatedSong = repository.get(1);
        assertEquals(1, updatedSong.getId());

        repository.load();
        var updatedUserFromFile = repository.get(1);
        assertEquals(1, updatedUserFromFile.getId());

    }

    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/songsDelete.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testSongsDelete.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new SongRepository(testDataPath);

        var song = new Song();
        song.setId(1);
        song.setSongName("Ups");

        repository.save(song);
        assertNotNull(repository.get(1));

        repository.delete(song);

        assertNull(repository.get(1));
    }

    @Test
    void get() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/songsGet.ser";

        var repository = new SongRepository(dataPath);

        var song = new Song();
        song.setSongName("QWERTY");
        repository.save(song);

        assertNotNull(repository.get(song.getId()));
    }

    @Test
    void getAll() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/songsGetAll.ser";

        var repository = new SongRepository(dataPath);


        repository.getAll().forEach(repository::delete);

        var song = new Song();
        song.setSongName("QWERTY");
        repository.save(song);

        var song2 = new Song();
        song2.setSongName("By Myself");
        repository.save(song2);

        var song3 = new Song();
        song3.setSongName("Faint");
        repository.save(song3);

        repository.getAll();
        assertEquals(3, repository.getAll().size());
    }
}
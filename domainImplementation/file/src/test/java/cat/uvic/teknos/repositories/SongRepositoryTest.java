package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class SongRepositoryTest {



    @Test
    void load() {
    }

    @Test
    void write() {
    }

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/songs.ser";

        var repository = new SongRepository(dataPath);

        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Linkin Park");
        artist.setMonthlyList(150000);

        var album = new Album();
        album.setId(1);
        album.setAlbumName("Meteora");
        album.setArtist(artist);
        album.setNSongs(13);


        var song = new Song();
        song.setId(2);
        song.setSongName("Breaking the habit");
        song.setDuration(196);
        song.setAlbum(album);
        song.setArtist(artist);

        repository.save(song);

        assertTrue(song.getId()>0);
        assertNotNull(repository.get(song.getId()));

        repository.load();

        assertNotNull(repository.get(song.getId()));
        assertEquals("Meteora", repository.get(song.getId()).getAlbum().getAlbumName());

    }

    @Test
    void update() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/updateSongs.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testUpdateSongs.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);


        var repository = new SongRepository(dataPath);


        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Linkin Park");
        artist.setMonthlyList(150000);

        var album = new Album();
        album.setId(1);
        album.setAlbumName("Meteora");
        album.setArtist(artist);
        album.setNSongs(13);


        var song = new Song();
        song.setId(1);
        song.setSongName("Numb");
        song.setDuration(206);
        song.setAlbum(album);
        song.setArtist(artist);


        repository.save(song);

        var updatedSong = repository.get(1);
        assertEquals(1, updatedSong.getId());

        repository.load();

        var updatedSongFromFile = repository.get(1);
        assertEquals(1, updatedSongFromFile.getId());

    }

    @Test
    void updateSongThatDoesntExist() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/updateSongs.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testUpdateSongs.ser";
        int id = 3;

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);



    }

    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/updateSongs.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testDeleteSongs.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);


        var repository = new SongRepository(dataPath);

        var song = new Song();
        song.setId(1);

        repository.save(song);

        var deletedSong = repository.get(1);
        assertEquals(1, deletedSong.getId());

        repository.load();

        var deletedSongFromFile = repository.get(1);
        assertEquals(1, deletedSongFromFile.getId());

    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }
}
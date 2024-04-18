package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.Album;
import cat.uvic.teknos.models.Artist;
import cat.uvic.teknos.models.ArtistData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class AlbumRepositoryTest {

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/albums.ser";

        var repository = new AlbumRepository(dataPath);

        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Linkin Park");

        var album = new Album();
        album.setAlbumName("Meteora");
        album.setNSongs(12);
        album.setArtist(artist);

        repository.save(album);

        assertTrue(album.getId()>0);
        assertNotNull(repository.get(album.getId()));
        assertNotNull(repository.get(album.getId()).getAlbumName());

        repository.load();
        assertNotNull(repository.get(album.getId()));
        assertEquals("Linkin Park", repository.get(album.getId()).getArtist().getGroupName());
    }

    @Test
    void updateAlbum() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/albumsUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testAlbumsUpdate.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new AlbumRepository(testDataPath);

        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Lady Gaga");

        var album = new Album();
        album.setId(1);
        album.setAlbumName("Born this way");
        album.setNSongs(8);
        album.setArtist(artist);

        repository.save(album);

        var updatedAlbum = repository.get(1);
        assertEquals(1, updatedAlbum.getId());

        repository.load();
        var updatedAlbumFromFile = repository.get(1);
        assertEquals(1, updatedAlbumFromFile.getId());

    }

    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/albumsDelete.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testAlbumsDelete.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new AlbumRepository(testDataPath);

        var album = new Album();
        album.setId(1);
        album.setAlbumName("Meteora");

        repository.save(album);
        assertNotNull(repository.get(1));

        repository.delete(album);

        assertNull(repository.get(1));
    }

    @Test
    void get() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/albumsGet.ser";

        var repository = new AlbumRepository(dataPath);

        var album = new Album();
        album.setAlbumName("Hybrid Theory");
        album.setNSongs(13);
        repository.save(album);

        assertNotNull(repository.get(album.getId()));
    }

    @Test
    void getAll() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/albumsGetAll.ser";

        var repository = new AlbumRepository(dataPath);

        repository.getAll().forEach(repository::delete);

        var album = new Album();
        album.setAlbumName("Hybrid Theory");
        album.setNSongs(13);
        repository.save(album);

        var album2 = new Album();
        album2.setAlbumName("Meteora");
        album2.setNSongs(11);
        repository.save(album2);

        var album3 = new Album();
        album3.setAlbumName("One more light");
        album3.setNSongs(14);
        repository.save(album3);

        repository.getAll();
        assertEquals(3, repository.getAll().size());
    }
}
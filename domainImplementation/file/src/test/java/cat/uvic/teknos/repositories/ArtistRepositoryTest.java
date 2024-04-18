package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.Artist;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class ArtistRepositoryTest {

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/artists.ser";

        var repository = new ArtistRepository(dataPath);

        var artist = new Artist();
        artist.setGroupName("Linkin Park");
        artist.setMonthlyList(150000);

        repository.save(artist);

        assertTrue(artist.getId()>0);
        assertNotNull(repository.get(artist.getId()));
        assertNotNull(repository.get(artist.getId()).getGroupName());

        repository.load();
        assertNotNull(repository.get(artist.getId()));
        assertEquals("Linkin Park", repository.get(artist.getId()).getGroupName());
    }

    @Test
    void updateArtist() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testArtistsUpdate.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new ArtistRepository(testDataPath);

        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Lady Gaga");
        artist.setMonthlyList(210000);

        repository.save(artist);

        var updatedArtist = repository.get(1);
        assertEquals(1, updatedArtist.getId());

        repository.load();
        var updatedArtistFromFile = repository.get(1);
        assertEquals(1, updatedArtistFromFile.getId());

    }
    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsDelete.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testArtistsDelete.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new ArtistRepository(testDataPath);

        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Whoops");

        repository.save(artist);
        assertNotNull(repository.get(1));

        repository.delete(artist);

        assertNull(repository.get(1));
    }

    @Test
    void get() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsGet.ser";

        var repository = new ArtistRepository(dataPath);

        var artist = new Artist();
        artist.setGroupName("The Offspring");
        repository.save(artist);

        assertNotNull(repository.get(artist.getId()));
    }

    @Test
    void getAll() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsGetAll.ser";

        var repository = new ArtistRepository(dataPath);

        repository.getAll().forEach(repository::delete);

        var artist = new Artist();
        artist.setGroupName("The Offspring");
        repository.save(artist);

        var artist2 = new Artist();
        artist2.setGroupName("Dr Peacock");
        repository.save(artist2);

        var artist3 = new Artist();
        artist3.setGroupName("Avenged Sevenfold");
        repository.save(artist3);

        repository.getAll();
        assertEquals(3, repository.getAll().size());
    }
}
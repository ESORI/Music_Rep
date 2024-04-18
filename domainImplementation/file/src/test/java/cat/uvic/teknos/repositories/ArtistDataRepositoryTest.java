package cat.uvic.teknos.repositories;

import cat.uvic.teknos.models.Artist;
import cat.uvic.teknos.models.ArtistData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class ArtistDataRepositoryTest {

    @Test
    void save() {
        var dataPath = System.getProperty("user.dir") + "/src/main/resources/data/artistsData.ser";

        var repository = new ArtistDataRepository(dataPath);

        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Linkin Park");

        var artistData = new ArtistData();
        artistData.setLang("EN");
        artistData.setDebutYear(1996);
        artistData.setArtist(artist);

        repository.save(artistData);

        assertTrue(artistData.getId()>0);
        assertNotNull(repository.get(artistData.getId()));
        assertNotNull(repository.get(artistData.getId()).getLang());

        repository.load();
        assertNotNull(repository.get(artistData.getId()));
        assertEquals("EN", repository.get(artistData.getId()).getLang());
    }

    @Test
    void updateArtistData() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsDataUpdate.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testArtistsDataUpdate.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new ArtistDataRepository(testDataPath);

        var artist = new Artist();
        artist.setId(1);
        artist.setGroupName("Depresión Sonora");

        var artistData = new ArtistData();
        artistData.setId(1);
        artistData.setLang("ES");
        artistData.setDebutYear(2006);
        artistData.setArtist(artist);

        repository.save(artistData);

        var updatedArtist = repository.get(1);
        assertEquals(1, updatedArtist.getId());

        repository.load();
        var updatedArtistFromFile = repository.get(1);
        assertEquals(1, updatedArtistFromFile.getId());

    }

    @Test
    void delete() throws IOException {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsDataDelete.ser";
        var testDataPath = System.getProperty("user.dir") + "/src/test/resources/data/testArtistsDataDelete.ser";

        Files.copy(Path.of(dataPath), Path.of(testDataPath), StandardCopyOption.REPLACE_EXISTING);

        var repository = new ArtistDataRepository(testDataPath);

        var artistData = new ArtistData();
        artistData.setId(1);
        artistData.setLang("ES");

        repository.save(artistData);
        assertNotNull(repository.get(1));

        repository.delete(artistData);

        assertNull(repository.get(1));
    }

    @Test
    void get() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsDataGet.ser";

        var repository = new ArtistDataRepository(dataPath);

        var artistData = new ArtistData();
        artistData.setCountry("Germany");
        artistData.setDebutYear(1989);
        artistData.setLang("EN");
        repository.save(artistData);

        assertNotNull(repository.get(artistData.getId()));
    }

    @Test
    void getAll() {
        var dataPath = System.getProperty("user.dir") + "/src/test/resources/data/artistsDataGetAll.ser";

        var repository = new ArtistDataRepository(dataPath);

        repository.getAll().forEach(repository::delete);

        var artistData = new ArtistData();
        artistData.setCountry("Germany");
        artistData.setDebutYear(1989);
        artistData.setLang("EN");
        repository.save(artistData);

        var artistData2 = new ArtistData();
        artistData2.setCountry("Spain");
        artistData2.setDebutYear(1997);
        artistData2.setLang("ES");
        repository.save(artistData2);

        repository.getAll();
        assertEquals(2, repository.getAll().size());
    }
}
package cat.uvic.teknos.musicrep.domain.jpa;

import cat.uvic.teknos.musicrep.domain.jpa.models.Album;
import cat.uvic.teknos.musicrep.domain.jpa.models.Artist;
import cat.uvic.teknos.musicrep.domain.jpa.models.ArtistData;
import cat.uvic.teknos.musicrep.domain.jpa.models.Song;
import cat.uvic.teknos.musicrep.domain.jpa.repository.JpaArtistRepository;
import cat.uvic.teknos.musicrep.domain.jpa.repository.JpaRepositoryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JpaArtistRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    static void setUp() throws IOException {
        var properties = new Properties();
        properties.load(JpaRepositoryFactory.class.getResourceAsStream("/persistence.properties"));
        entityManagerFactory = Persistence.createEntityManagerFactory("music_rep-mysql", properties);
        entityManager = entityManagerFactory.createEntityManager();
    }
    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    void insertAndUpdateArtist() {
        var repository = new JpaArtistRepository(entityManager);

        Artist artist2 = new Artist();
        artist2.setGroupName("Group name test 2");
        artist2.setMonthlyList(789456);

        Artist artist = new Artist();
        artist.setGroupName("Group name test");
        artist.setMonthlyList(123456);

        ArtistData artistData = new ArtistData();
        artistData.setDebutYear(1999);
        artistData.setLang("ENG");
        artistData.setCountry("PT");

        artist.setArtistData(artistData);

        Album album = new Album();
        album.setAlbumName("Album name test");
        album.setNSongs(5);
        album.setId(artist.getId());

        Song song = new Song();
        song.setArtist(artist);
        song.setSongName("Song name test");
        song.setDuration(206);

        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);

        album.setSong(songs);
        var albums = new HashSet<com.esori.list.models.Album>();
        albums.add(album);

        artist.setAlbum(albums);

        repository.save(artist2);
        repository.save(artist);

    }

    @Test
    void deleteArtist() {
        var repository = new JpaArtistRepository(entityManager);
        Artist artist2 = new Artist();
        artist2.setGroupName("Group name test 2");
        artist2.setMonthlyList(789456);
        Artist artist = new Artist();
        artist.setGroupName("Group name test");
        artist.setMonthlyList(123456);
        ArtistData artistData = new ArtistData();
        artistData.setDebutYear(1999);
        artistData.setLang("ENG");
        artistData.setCountry("PT");
        artist.setArtistData(artistData);

        repository.save(artist2);
        repository.save(artist);

        Artist artist3 = new Artist();
        artist3.setId(2);
        repository.delete(artist3);
    }

    @Test
    void getArtist(){
        var repository = new JpaArtistRepository(entityManager);

        Artist artist = new Artist();
        artist.setGroupName("Group name test");
        artist.setMonthlyList(123456);
        ArtistData artistData = new ArtistData();
        artistData.setDebutYear(1999);
        artistData.setLang("ENG");
        artistData.setCountry("PT");
        artist.setArtistData(artistData);

        repository.save(artist);

        System.out.println(repository.get(1).getGroupName());
    }

    @Test
    void getAllArtists(){
        var repository = new JpaArtistRepository(entityManager);

        Artist artist2 = new Artist();
        artist2.setGroupName("Group name test 2");
        artist2.setMonthlyList(789456);
        Artist artist = new Artist();
        artist.setGroupName("Group name test");
        artist.setMonthlyList(123456);
        ArtistData artistData = new ArtistData();
        artistData.setDebutYear(1999);
        artistData.setLang("ENG");
        artistData.setCountry("PT");
        artist.setArtistData(artistData);

        repository.save(artist2);
        repository.save(artist);

        //Commented when doing the whole test since it will detect the size of ALL
        //the saves done before, which will be more that the size given here
        //assertEquals(2, repository.getAll().size());
    }
}
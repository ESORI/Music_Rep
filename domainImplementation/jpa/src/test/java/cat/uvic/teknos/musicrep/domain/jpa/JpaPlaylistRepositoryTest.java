package cat.uvic.teknos.musicrep.domain.jpa;

import cat.uvic.teknos.musicrep.domain.jpa.models.Playlist;
import cat.uvic.teknos.musicrep.domain.jpa.models.Song;
import cat.uvic.teknos.musicrep.domain.jpa.models.User;
import cat.uvic.teknos.musicrep.domain.jpa.repository.JpaPlaylistRepository;
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

class JpaPlaylistRepositoryTest {
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
    void insertAndUpdatePlaylist(){
        var repository = new JpaPlaylistRepository(entityManager);
        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Name test");
        playlist.setDescription("Description test");
        playlist.setDuration(500);
        playlist.setNSongs(2);

        User user = entityManager.find(User.class,1);
        User user1 = entityManager.find(User.class,2);


        var users = new HashSet<com.esori.list.models.User>();
        users.add(user);
        users.add(user1);

        Song song = entityManager.find(Song.class,1);
        Song song2 = entityManager.find(Song.class,2);

        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);
        songs.add(song2);

        playlist.setSong(songs);
        playlist.setUser(users);

        playlist.setUser(users);
        repository.save(playlist);
    }

    @Test
    void deletePlaylist(){
        var repository = new JpaPlaylistRepository(entityManager);
        Playlist playlist = entityManager.find(Playlist.class, 1);
        repository.delete(playlist);
    }

    @Test
    void getPlaylist(){
        var repository = new JpaPlaylistRepository(entityManager);
        System.out.println(repository.get(1).getPlaylistName());

    }

    @Test
    void getAllPlaylists(){
        var repository = new JpaPlaylistRepository(entityManager);
        System.out.println(repository.getAll().size());
    }

}
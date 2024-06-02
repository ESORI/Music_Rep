package cat.uvic.teknos.musicrep.domain.jpa.repository;

import com.esori.list.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.util.Properties;

public class JpaRepositoryFactory implements RepositoryFactory {

    private final EntityManager entityManager;

    public JpaRepositoryFactory() throws IOException {
        var properties = new Properties();
        properties.load(JpaRepositoryFactory.class.getResourceAsStream("/persistence.properties"));
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("music_rep-mysql", properties);
        entityManager = entityManagerFactory.createEntityManager();
    }
    @Override
    public UserRepository getUserRepository() {
        return new JpaUserRepository(entityManager);
    }


    @Override
    public ArtistRepository getArtistRepository() {
        return new JpaArtistRepository(entityManager);
    }

    @Override
    public PlaylistRepository getPlaylistRepository() {
        return new JpaPlaylistRepository(entityManager);
    }
}



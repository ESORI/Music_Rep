package cat.uvic.teknos.musicrep.domain.jpa;
/*
import cat.uvic.teknos.musicrep.domain.jpa.models.Album;
import cat.uvic.teknos.musicrep.domain.jpa.models.Artist;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {
    private static EntityManagerFactory entityManagerFactory;
    @BeforeAll
    static void setUp(){
        entityManagerFactory = Persistence.createEntityManagerFactory("music_rep-mysql");
    }

    @AfterAll
    static void tearDown(){
        entityManagerFactory.close();
    }

    @Test
    void insertTest(){
        //EntityManager
        //  manages transactions
        //  manages entitles
        //  manages cache
        var entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            //var x = entityManager.find(x.

            var artist = new Artist();
            artist.setGroupName("Linkin Park");
            artist.setMonthlyList(450000);

            var album = new Album();
            album.setAlbumName("Meteora");
            album.setNSongs(15);

            var album2 = new Album();
            album.setAlbumName("Hybrid Theory");
            album.setNSongs(12);

            artist.getAlbum().add(album);
            artist.getAlbum().add(album2);

            entityManager.persist(artist);
            assertTrue(artist.getId()>0);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

}

 */
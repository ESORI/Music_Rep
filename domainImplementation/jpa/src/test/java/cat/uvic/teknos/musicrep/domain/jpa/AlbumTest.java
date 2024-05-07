package cat.uvic.teknos.musicrep.domain.jpa;
/*
import cat.uvic.teknos.musicrep.domain.jpa.models.Album;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
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



            var album = new Album();
            album.setAlbumName("Meteora");
            album.setNSongs(15);





            entityManager.persist(album);
            assertTrue(album.getId()>0);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

}

 */
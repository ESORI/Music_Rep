package cat.uvic.teknos.musicrep.domain.jpa;

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

            var artist = new Artist();
            artist.setGroupName("Linkin Park");
            artist.setMonthlyList(450000);

            entityManager.persist(artist);
            assertTrue(artist.getId()>0);

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

}
package cat.uvic.teknos.musicrep.domain.jpa.repository;

import cat.uvic.teknos.musicrep.domain.jpa.models.User;
import cat.uvic.teknos.musicrep.domain.jpa.models.UserData;
import com.esori.list.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JpaUserRepositoryTest {
    private static EntityManagerFactory entityManagerFactory;
    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("music_rep");
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    void insertUser() {
        var entityManager = entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();

            User user = new User();
            user.setUsername("Test");
            UserData userData = new UserData();
            userData.setUserName("Name Test");
            userData.setAge(22);
            userData.setCountry("NZ");

            user.setUserData(userData);

            entityManager.persist(userData);
            entityManager.persist(user);

            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
        }


    }

    @Test
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByUsername() {
    }
}
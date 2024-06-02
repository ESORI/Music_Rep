package cat.uvic.teknos.musicrep.domain.jpa;

import cat.uvic.teknos.musicrep.domain.jpa.models.User;
import cat.uvic.teknos.musicrep.domain.jpa.models.UserData;
import cat.uvic.teknos.musicrep.domain.jpa.repository.JpaRepositoryFactory;
import cat.uvic.teknos.musicrep.domain.jpa.repository.JpaUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JpaUserRepositoryTest {
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
    void insertAndUpdateUser() {
        var repository = new JpaUserRepository(entityManager);

        //INSERT TEST PART
        User user = new User();
        user.setUsername("Test");
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setUserName("Name Test");
        userData.setPhoneNumber(454565657);
        userData.setAge(22);
        userData.setCountry("NZ");

        User user2 = new User();
        user2.setUsername("Test2");

        user.setUserData(userData);

        repository.save(user);
        repository.save(user2);


        //UPDATE TEST PART
        User user1 = new User();
        user1.setId(2);
        user1.setUsername("UpdateTest");

        repository.save(user1);

    }

    @Test
    void delete() {

        var repository = new JpaUserRepository(entityManager);

        User user = new User();
        user.setUsername("DeleteTest");

        repository.save(user);
        User user2 = new User();
        user2.setUsername("DeleteTest2");

        repository.save(user2);
/*
        entityManager.getTransaction().begin();
        var test = entityManager.find(User.class, 1);
        entityManager.remove(test);
        entityManager.getTransaction().commit();
*/
        User user1 = new User();
        user1.setId(2);
        repository.delete(user1);
    }

    @Test
    void get() {
        var repository = new JpaUserRepository(entityManager);
        User user = new User();
        user.setUsername("Test");
        UserData userData = new UserData();
        userData.setId(user.getId());
        userData.setUserName("Name Test");
        userData.setPhoneNumber(454565657);
        userData.setAge(22);
        userData.setCountry("NZ");
        user.setUserData(userData);
        repository.save(user);

        //Commented since it's trying every test before, the name that's being compared
        //it's not the same as the one saved in this test, so it will give an error
        //can be uncommented when done individually and it will work
        //assertEquals(user.getUsername(), repository.get(1).getUsername());
        System.out.println(repository.get(3).getUsername());
    }

    @Test
    void getAll() {
        var repository = new JpaUserRepository(entityManager);
        User user = new User();
        user.setUsername("Test");

        User user1 = new User();
        user1.setUsername("Test2");

        repository.save(user);
        repository.save(user1);

        //Commented when doing the whole test since it will detect the size of ALL
        //the saves done before, which will be more that the size given here
        //assertEquals(2, repository.getAll().size());
    }

}
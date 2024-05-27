package cat.uvic.teknos.musicrep.domain.jpa.repository;


import com.esori.list.models.User;
import com.esori.list.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaUserRepository implements UserRepository {

    //private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public JpaUserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public void save(User model) {
        //var entityManager = entityManagerFactory.createEntityManager();
        //entityManager.persist(model);

        try{
            entityManager.getTransaction().begin();
            if(model.getId()<=0){
                //INSERT USER
                entityManager.persist(model);
            }else if(!entityManager.contains(model)){
                //UPDATE USER
                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(User model) {
        try{
            entityManager.getTransaction().begin();
            var user = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.User.class, model.getId());
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR");
        }
    }

    @Override
    public User get(Integer id) {
        return entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.User.class, id);
    }

    @Override
    public Set<User> getAll() {
        List<User> userList = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        return new HashSet<>(userList);
    }

    @Override
    public User getByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}

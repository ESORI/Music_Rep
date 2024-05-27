package cat.uvic.teknos.musicrep.domain.jpa.repository;

import com.esori.list.models.Artist;
import com.esori.list.models.Playlist;
import com.esori.list.repositories.PlaylistRepository;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaPlaylistRepository implements PlaylistRepository {
    private final EntityManager entityManager;

    public JpaPlaylistRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    @Override
    public void save(Playlist model) {

        try{
            entityManager.getTransaction().begin();
            if(model.getId()<=0){
                //INSERT PLAYLIST
                entityManager.persist(model);
            }else if(!entityManager.contains(model)){
                //UPDATE PLAYLIST
                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Playlist model) {
        try{
            entityManager.getTransaction().begin();
            var playlist = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.Playlist.class, model.getId());
            entityManager.remove(playlist);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR");
        }
    }

    @Override
    public Playlist get(Integer id) {
        return entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.Playlist.class, id);

    }

    @Override
    public Set<Playlist> getAll() {
        List<Playlist> playlistList = entityManager.createQuery("SELECT u FROM Playlist u", Playlist.class).getResultList();
        return new HashSet<>(playlistList);
    }
}

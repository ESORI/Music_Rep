package cat.uvic.teknos.musicrep.domain.jpa.repository;

import cat.uvic.teknos.musicrep.domain.jpa.models.Song;
import com.esori.list.models.Album;
import com.esori.list.models.Artist;
import com.esori.list.repositories.ArtistRepository;
import jakarta.persistence.EntityManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JpaArtistRepository implements ArtistRepository {

    private final EntityManager entityManager;

    public JpaArtistRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void save(Artist model) {

        try{
            entityManager.getTransaction().begin();
            if(model.getId()<=0){
                //INSERT ARTIST
                entityManager.persist(model);
            }else if(!entityManager.contains(model)){
                //UPDATE ARTIST

                if(model.getArtistData()==null){
                    var artistData = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.ArtistData.class, model.getId());
                    model.setArtistData(artistData);
                }

                entityManager.merge(model);
            }
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
    }

    @Override
    public void delete(Artist model) {
        try{
            entityManager.getTransaction().begin();
            Artist artist = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.Artist.class, model.getId());
            entityManager.remove(artist);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("ERROR");
        }
    }

    @Override
    public Artist get(Integer id) {
        return entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.Artist.class, id);
    }

    @Override
    public Set<Artist> getAll() {
        List<Artist> artistList = entityManager.createQuery("SELECT u FROM Artist u", Artist.class).getResultList();
        return new HashSet<>(artistList);
    }
}



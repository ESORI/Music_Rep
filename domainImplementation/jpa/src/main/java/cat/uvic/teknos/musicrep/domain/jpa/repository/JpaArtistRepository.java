package cat.uvic.teknos.musicrep.domain.jpa.repository;

import com.esori.list.models.Album;
import com.esori.list.models.Artist;
import com.esori.list.models.Song;
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
                var artist = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.Artist.class, model.getId());
                if(model.getArtistData()==null){
                    var artistData = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.ArtistData.class, model.getId());
                    model.setArtistData(artistData);
                }

                if(model.getAlbum().isEmpty()){
                    model.setAlbum(artist.getAlbum());
                }else{
                    for(var album : model.getAlbum()){
                        var albumOG = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.Album.class, album.getId());
                        if(album.getSong().isEmpty()){
                            album.setSong(albumOG.getSong());
                        }
                    }
                }

                entityManager.merge(model);
                System.out.println("Worked?");
            }
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println("DIDN'T WORK");
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



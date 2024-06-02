package cat.uvic.teknos.musicrep.domain.jpa.repository;

import com.esori.list.models.Artist;
import com.esori.list.models.Playlist;
import com.esori.list.models.Song;
import com.esori.list.models.User;
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
            saveUser(model);
            saveSong(model);
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

    private void saveUser(Playlist model) {
        if(model.getUser()!=null){
            var users = new HashSet<User>();
            for(var user: model.getUser()){
                var addedUser = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.User.class, user.getId());
                users.add(addedUser);
            }
            model.setUser(users);
        }
    }

    private void saveSong(Playlist model) {
        if(model.getSong()!=null){
            var songs = new HashSet<Song>();
            for(var song: model.getSong()){
                var addedSong = entityManager.find(cat.uvic.teknos.musicrep.domain.jpa.models.Song.class, song.getId());
                songs.add(addedSong);
            }
            model.setSong(songs);
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

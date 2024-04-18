package cat.uvic.teknos.repositories;

import com.esori.list.models.Playlist;
import com.esori.list.models.Song;
import com.esori.list.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SongRepository implements com.esori.list.repositories.SongRepository {

    private static Map<Integer, Song> songs = new HashMap<>();
    private final String dataPath;

    public SongRepository(String dataPath) {
        this.dataPath = dataPath;

        load();
    }

    void load() {
        var file = new File(dataPath);
        if(file.exists()) {
            try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                songs = (Map<Integer, Song>) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void write(){
        //var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))){
            outputStream.writeObject(songs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Song model) {
        if(model.getId() <= 0){
            // get new id
            var newId = songs.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);
            songs.put(newId, model);
        }else{
            if (songs.get(model.getId()) == null) {
                throw new RuntimeException("User with id " + model.getId() + " not found");
            }
            songs.put(model.getId(), model);
        }
        write();
    }

    @Override
    public void delete(Song model) {
        songs.remove(model.getId());
        write();
    }

    @Override
    public Song get(Integer id) {
        return songs.get(id);
    }

    @Override
    public Set<Song> getAll() {
        return Set.copyOf(songs.values());
    }
}

package cat.uvic.teknos.repositories;

import com.esori.list.models.Playlist;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PlaylistRepository implements com.esori.list.repositories.PlaylistRepository {

    private static Map<Integer, Playlist> playlists = new HashMap<>();
    private final String dataPath;

    public PlaylistRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    void load() {
        var file = new File(dataPath);
        if(file.exists()) {
            try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                playlists = (Map<Integer, Playlist>) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void write(){
        //var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))){
            outputStream.writeObject(playlists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Playlist model) {
        if(model.getId() <= 0){
            // get new id
            var newId = playlists.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);
            playlists.put(newId, model);
        }else{
            playlists.put(model.getId(), model);
        }
        write();
    }

    @Override
    public void delete(Playlist model) {
        playlists.remove(model.getId());
        write();
    }

    @Override
    public Playlist get(Integer id) {
        return playlists.get(id);
    }

    @Override
    public Set<Playlist> getAll() {
        return Set.copyOf(playlists.values());
    }
}

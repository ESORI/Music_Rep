package cat.uvic.teknos.repositories;

import com.esori.list.models.Album;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlbumRepository implements com.esori.list.repositories.AlbumRepository {

    private static Map<Integer, Album> albums = new HashMap<>();
    private final String dataPath;

    public AlbumRepository(String dataPath) {
        this.dataPath = dataPath;
    }

    void load() {
        try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
            albums = (Map<Integer, Album>) inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    void write(){
        //var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))){
            outputStream.writeObject(albums);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Album model) {
        if(model.getId() <= 0){
            // get new id
            var newId = albums.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);
            albums.put(newId, model);
        }else{
            albums.put(model.getId(), model);
        }
        write();
    }

    @Override
    public void delete(Album model) {
        albums.remove(model.getId());
    }

    @Override
    public void update(Album model) {
        if(model.getId() > 0){
            var id = model.getId();
            albums.put(id, model);
        }else{
            save(model);
        }
        write();
    }

    @Override
    public Album get(Integer id) {
        return albums.get(id);
    }

    @Override
    public Set<Album> getAll() {
        return Set.copyOf(albums.values());
    }
}

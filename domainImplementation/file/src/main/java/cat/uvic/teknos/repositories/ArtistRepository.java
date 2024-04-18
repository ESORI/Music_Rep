package cat.uvic.teknos.repositories;

import com.esori.list.models.Artist;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArtistRepository implements com.esori.list.repositories.ArtistRepository {

    private static Map<Integer, Artist> artists = new HashMap<>();
    private final String dataPath;

    public ArtistRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    void load() {
        var file = new File(dataPath);
        if(file.exists()) {
            try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                artists = (Map<Integer, Artist>) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void write(){
        //var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))){
            outputStream.writeObject(artists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(Artist model) {
        if(model.getId() <= 0){
            // get new id
            var newId = artists.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);
            artists.put(newId, model);
        }else{
            artists.put(model.getId(), model);
        }
        write();
    }

    @Override
    public void delete(Artist model) {
        artists.remove(model.getId());
        write();
    }

    @Override
    public Artist get(Integer id) {
        return artists.get(id);
    }

    @Override
    public Set<Artist> getAll() {
        return Set.copyOf(artists.values());
    }
}

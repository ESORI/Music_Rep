package cat.uvic.teknos.repositories;

import com.esori.list.models.ArtistData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ArtistDataRepository implements com.esori.list.repositories.ArtistDataRepository {

    private static Map<Integer, ArtistData> artistsData = new HashMap<>();
    private final String dataPath;

    public ArtistDataRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    void load() {
        var file = new File(dataPath);
        if(file.exists()) {
            try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                artistsData = (Map<Integer, ArtistData>) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    void write(){
        //var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))){
            outputStream.writeObject(artistsData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(ArtistData model) {
        if(model.getId() <= 0){
            // get new id
            var newId = artistsData.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);
            artistsData.put(newId, model);
        }else{
            artistsData.put(model.getId(), model);
        }
        write();
    }

    @Override
    public void delete(ArtistData model) {
        artistsData.remove(model.getId());
        write();
    }

    @Override
    public ArtistData get(Integer id) {
        return artistsData.get(id);
    }

    @Override
    public Set<ArtistData> getAll() {
        return Set.copyOf(artistsData.values());
    }
}

package cat.uvic.teknos.repositories;

import com.esori.list.models.UserData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserDataRepository implements com.esori.list.repositories.UserDataRepository {

    private Map<Integer, UserData> usersData = new HashMap<>();
    private final String dataPath;

    public UserDataRepository(String dataPath) {
        this.dataPath = dataPath;
        load();
    }

    void load() {
        var file = new File(dataPath);
        if(file.exists()){
            try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
                usersData = (Map<Integer, UserData>) inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    void write(){
        //var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))){
            outputStream.writeObject(usersData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(UserData model) {
        if(model.getId() <= 0){
            // get new id
            var newId = usersData.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);
            usersData.put(newId, model);
        }else{
            if (usersData.get(model.getId()) == null) {
                throw new RuntimeException("User with id " + model.getId() + " not found");
            }
            usersData.put(model.getId(), model);
        }
        write();
    }

    @Override
    public void delete(UserData model) {
        usersData.remove(model.getId());
        write();
    }

    @Override
    public UserData get(Integer id) {
        return usersData.get(id);
    }

    @Override
    public Set<UserData> getAll() {
        return Set.copyOf(usersData.values());
    }
}

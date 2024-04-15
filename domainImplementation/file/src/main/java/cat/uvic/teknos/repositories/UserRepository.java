package cat.uvic.teknos.repositories;

import com.esori.list.models.User;
import com.esori.list.models.UserData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserRepository implements com.esori.list.repositories.UserRepository {

    private static Map<Integer, User> users = new HashMap<>();
    private final String dataPath;

    public UserRepository(String dataPath) {
        this.dataPath = dataPath;
    }

    void load() {
        try (var inputStream = new ObjectInputStream(new FileInputStream(dataPath))) {
            users = (Map<Integer, User>) inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    void write(){
        //var dataDirectory = System.getProperty("user.dir") + "/src/main/resources/data/";

        try (var outputStream = new ObjectOutputStream(new FileOutputStream(dataPath))){
            outputStream.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User model) {
        if(model.getId() <= 0){
            // get new id
            var newId = users.keySet().stream().mapToInt(k -> k).max().orElse(0) + 1;
            model.setId(newId);
            users.put(newId, model);
        }else{
            users.put(model.getId(), model);
        }
        write();
    }
    @Override
    public void update(User model) {
        if(model.getId() > 0){
            var id = model.getId();
            users.put(id, model);
        }else{
            save(model);
        }
        write();
    }

    @Override
    public void delete(User model) {
        users.remove(model.getId());
    }

    @Override
    public User get(Integer id) {
        return users.get(id);
    }

    @Override
    public Set<User> getAll() {
        return Set.copyOf(users.values());
    }
}

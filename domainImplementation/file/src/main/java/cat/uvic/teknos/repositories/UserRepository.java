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
        load();
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
            if (users.get(model.getId()) == null) {
                throw new RuntimeException("User with id " + model.getId() + " not found");
            }
            users.put(model.getId(), model);
        }
        write();
    }


    @Override
    public void delete(User model) {
        users.remove(model.getId());
        write();
    }

    @Override
    public User get(Integer id) {
        return users.get(id);
    }

    @Override
    public Set<User> getAll() {
        return Set.copyOf(users.values());
    }

    @Override
    public User getByUsername(String username) {
        return users.values().stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }
}

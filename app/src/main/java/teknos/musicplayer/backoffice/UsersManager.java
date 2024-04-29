package teknos.musicplayer.backoffice;

import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.UserRepository;

import java.io.BufferedReader;
import java.io.PrintStream;

import static teknos.musicplayer.backoffice.IOUtils.readLine;

public class UsersManager {
    private final PrintStream out;
    private final BufferedReader in;
    private final UserRepository userRepository;
    private final ModelFactory modelFactory;

    public UsersManager(BufferedReader in, PrintStream out, UserRepository userRepository, ModelFactory modelFactory) {
        this.out = out;
        this.in = in;
        this.userRepository = userRepository;
        this.modelFactory = modelFactory;
    }

    public void start(){
        out.println("Users: ");

        var command = "";
        do{
            command = readLine(in);

            switch (command){
                case"1" -> insert();
                //case"2" -> update();
            }

        }while(!command.equals("exit"));
        out.println("Bye!");
    }

    private void insert() {
        var user = modelFactory.createUser();
        userRepository.save(null);
    }
}


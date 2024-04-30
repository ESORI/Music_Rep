package teknos.musicplayer.backoffice;

import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.UserRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

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
        out.println("Type:");
        out.println("1 to insert a new User");

        var command = "";
        do{
            command = readLine(in);

            switch (command){
                case"1" -> insert();
                case"2" -> update();
                case"5" -> getAll();
            }

        }while(!command.equals("exit"));
        out.println("Bye!");
    }

    private void getAll() {
        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "Username");
        asciiTable.addRule();

        for(var user : userRepository.getAll()){
            asciiTable.addRow(user.getId(), user.getUsername());
            asciiTable.addRule();
        }

        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        System.out.println(render);
    }

    private void update() {
        var user = modelFactory.createUser();
        userRepository.save(user);
    }

    private void insert() {
        var user = modelFactory.createUser();

        out.println("Username: ");
        user.setUsername(readLine(in));

        //out.println("User name:");
        //user.setUserData();

        userRepository.save(user);

        out.println("Insert user succesfully: "+ user);
    }
}


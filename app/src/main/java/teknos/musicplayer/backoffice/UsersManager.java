package teknos.musicplayer.backoffice;


import com.esori.list.models.ModelFactory;
import com.esori.list.models.User;
import com.esori.list.models.UserData;
import com.esori.list.repositories.UserRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import static teknos.musicplayer.backoffice.IOUtils.readLine;


public class UsersManager {
    private final PrintStream out;
    private final BufferedReader in;
    private final UserRepository userRepository;
    private final ModelFactory modelFactory;
    private final Properties properties = new Properties();


    public UsersManager(BufferedReader in, PrintStream out, UserRepository userRepository, ModelFactory modelFactory) throws IOException {
        this.out = out;
        this.in = in;
        this.userRepository = userRepository;
        this.modelFactory = modelFactory;
        properties.load(App.class.getResourceAsStream("/app.properties"));
    }


    public void start() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        var command = "";
        do{
            showMenu();
            command = readLine(in);

            switch (command){
                case"1" -> insert();
                case"2" -> update();
                case"3" -> delete();
                case"4" -> get();
                case"5" -> getAll();
                default -> {
                    if(!command.equalsIgnoreCase("exit")){out.println("Invalid command");}
                }
            }

        }while(!command.equalsIgnoreCase("exit"));
        out.println("Bye!");
    }

    private void showMenu() {
        out.println("***Users Manager***");
        out.println("Type:");
        out.println("1 to insert a new User");
        out.println("2 to update User");
        out.println("3 to delete User");
        out.println("4 to get an User");
        out.println("5 to show all Users");
        out.println("'exit' to exit");
    }

    private void delete(){
        var user = modelFactory.createUser();

        out.println("Please enter the user id you wish to delete");
        int id = Integer.parseInt(readLine(in));
        user.setId(id);
        userRepository.delete(user);
    }

    private void getAll() {
        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        //ONLY GETS USERNAME TO SEE WHAT USER ARE IN THE REPOSITORY, THEN WE WILL BE ABLE TO GET EVERY DATA
        //FROM USING GET() SINCE I CONSIDER IT SHOULD BE MORE SPECIFIC
        asciiTable.addRow("Id", "Username");
        asciiTable.addRule();

        for(var user : userRepository.getAll()){
            asciiTable.addRow(user.getId(), user.getUsername());
            asciiTable.addRule();
        }

        getTable(asciiTable);
    }

    private void get(){
        var asciiTable = new AsciiTable();
        out.println("Please enter the user id you wish to search");
        int id = Integer.parseInt(readLine(in));
        var user = userRepository.get(id);

        int age;
        int phoneNum;
        String country;
        if(user.getUserData()!=null){
            age = user.getUserData().getAge();
            phoneNum = user.getUserData().getPhoneNumber();
            country = user.getUserData().getCountry();
        }else{
            age = 0;
            phoneNum = 0;
            country = "No data";
        }
        asciiTable.addRule();
        asciiTable.addRow("Username", "Name", "Age", "Phone Number" ,"Country");
        asciiTable.addRule();

        asciiTable.addRow(user.getUsername(), user.getUserData().getUserName(),
                age, phoneNum, country);
        asciiTable.addRule();

        getTable(asciiTable);
    }

    private static void getTable(AsciiTable asciiTable) {
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        String render = asciiTable.render();
        System.out.println(render);
    }

    private void update() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var user = modelFactory.createUser();
        var userData = (UserData) Class.forName(properties.getProperty("userData")).getConstructor().newInstance();

        out.println("Please enter the user's id you wish to update");
        int id = Integer.parseInt(readLine(in));
        user.setId(id);
        userData.setId(id);

        out.println("Username: ");
        user.setUsername(readLine(in));


        out.println("Do you want to update the user's data? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            setUserData(user, userData);
        }


        userRepository.save(user);
    }

    private void setUserData(User user, UserData userData) {
        out.println("User name:");
        userData.setUserName(readLine(in));

        out.println("Phone number:");
        userData.setPhoneNumber(Integer.parseInt(readLine(in)));
        out.println("Country:");
        userData.setCountry(readLine(in));
        out.println("Age:");
        userData.setAge(Integer.parseInt(readLine(in)));

        user.setUserData(userData);
    }

    private void insert() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var user = modelFactory.createUser();
        var userData = (UserData) Class.forName(properties.getProperty("userData")).getConstructor().newInstance();

        out.println("Username: ");
        user.setUsername(readLine(in));

        out.println("Do you want to add Data to the user? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            setUserData(user, userData);
        }

        userRepository.save(user);

        out.println("Inserted user succesfully: "+ user);
    }
}


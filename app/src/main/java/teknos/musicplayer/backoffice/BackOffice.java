package teknos.musicplayer.backoffice;

import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.RepositoryFactory;
import teknos.musicplayer.backoffice.exceptions.BackOfficeException;

import static teknos.musicplayer.backoffice.IOUtils.*;
import java.io.*;

public class BackOffice {
    private final BufferedReader in;
    private final PrintStream out;
    private final RepositoryFactory repositoryFactory;
    private final ModelFactory modelFactory;

    public BackOffice (InputStream inputStream, OutputStream outputStream, RepositoryFactory repositoryFactory, ModelFactory modelFactory){
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        this.out = new PrintStream(outputStream);
        this.repositoryFactory = repositoryFactory;
        this.modelFactory = modelFactory;
    }

    public void start(){
        showWelcomeMessage();

        var command = "";
        do{
            showMainMenu();
            command = readLine(in);

            switch (command){
                case "1"-> manageArtists();
                case "2"-> manageSongs();
                case "3"-> managePlaylists();
                case "4"->manageUsers();
            }

        }while(!command.equals("exit"));

        out.println("Bye!");

    }

    private void manageUsers() {
        new UsersManager(in, out, repositoryFactory.getUserRepository(), modelFactory).start();
    }

    private void managePlaylists() {
        out.println("Playlists");
    }

    private void manageSongs() {
        out.println("Songs");
    }

    private void manageArtists() {
        out.println("Artists");
    }


    private void showWelcomeMessage() {
        out.println("Welcome to the Music Player Back Office");
        out.println("Select a menu option or type exit to exit the application");
    }

    private void showMainMenu() {
        out.println("1. Artist");
        out.println("2. Song");
        out.println("3. Playlist");
        out.println("4. User");
    }
}

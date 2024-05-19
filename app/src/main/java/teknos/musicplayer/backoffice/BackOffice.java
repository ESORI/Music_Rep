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
                case "2"-> managePlaylists();
                case "3"->manageUsers();
                default -> out.println("Invalid command");
            }

        }while(!command.equalsIgnoreCase("exit"));

        out.println("Bye!");

    }

    private void manageUsers() {
        new UsersManager(in, out, repositoryFactory.getUserRepository(), modelFactory).start();
    }

    private void managePlaylists() {
        new PlaylistsManager(in, out, repositoryFactory.getPlaylistRepository(), modelFactory).start();
    }


    private void manageArtists() {
        new ArtistsManager(in, out, repositoryFactory.getArtistRepository(), modelFactory).start();
    }


    private void showWelcomeMessage() {
        out.println("Welcome to the Music Player Back Office");
        out.println("Select a menu option or type exit to exit the application");
    }

    private void showMainMenu() {
        out.println("1. Artist");
        out.println("2. Playlist");
        out.println("3. User");
    }
}

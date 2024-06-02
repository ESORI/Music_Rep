package teknos.musicplayer.backoffice;

import com.esori.list.models.ModelFactory;
import com.esori.list.models.Song;
import com.esori.list.models.User;
import com.esori.list.repositories.PlaylistRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Properties;

import static teknos.musicplayer.backoffice.IOUtils.readLine;

public class PlaylistsManager {

    private final PrintStream out;
    private final BufferedReader in;
    private final PlaylistRepository playlistRepository;
    private final ModelFactory modelFactory;
    private final Properties properties = new Properties();

    public PlaylistsManager(BufferedReader in, PrintStream out, PlaylistRepository playlistRepository, ModelFactory modelFactory) throws IOException {
        this.out = out;
        this.in = in;
        this.playlistRepository = playlistRepository;
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
        out.println("***Playlists Manager***");
        out.println("Type:");
        out.println("1 to insert a new Playlist");
        out.println("2 to update Playlist");
        out.println("3 to delete Playlist");
        out.println("4 to get a Playlist");
        out.println("5 to show all Playlists");
        out.println("'exit' to exit");
    }

    private void insert() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playlist = modelFactory.createPlaylist();
        var users = new HashSet<com.esori.list.models.User>();
        var songs = new HashSet<com.esori.list.models.Song>();

        out.println("Playlist name:");
        playlist.setPlaylistName(readLine(in));
        out.println("Description: ");
        playlist.setDescription(readLine(in));
        out.println("Total duration of playlist?");
        playlist.setDuration(Integer.parseInt(readLine(in)));

        out.println("Do you want to add any Users in the playlist?(yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("How many?");
            int totalUsers = Integer.parseInt(readLine(in));
            insertUsers(totalUsers, users);
        }

        out.println("Hoy many songs would you like to add to your playlist?(0,1...)");
        int totalSongs = Integer.parseInt(readLine(in));
        playlist.setNSongs(totalSongs);
        if(totalSongs>0){
            insertSongs(totalSongs, songs);
        }

        playlist.setSong(songs);
        playlist.setUser(users);
        playlistRepository.save(playlist);
        out.println("Inserted artist succesfully: "+ playlist);
    }

    private void insertUsers(int totalUsers, HashSet<User> users) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(int i1 = 0; i1 < totalUsers; i1++){
            var user = (User) Class.forName(properties.getProperty("user")).getConstructor().newInstance();
            out.println("Please add the user's id");
            user.setId(Integer.parseInt(readLine(in)));
            users.add(user);
        }
    }

    private void insertSongs(int totalSongs, HashSet<com.esori.list.models.Song> songs) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(int i = 0; i< totalSongs; i++){
            var song = (Song) Class.forName(properties.getProperty("song")).getConstructor().newInstance();
            out.println("Please add the song's id");
            song.setId(Integer.parseInt(readLine(in)));
            songs.add(song);
        }
    }

    private void update() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playlist = modelFactory.createPlaylist();
        var users = new HashSet<com.esori.list.models.User>();
        var songs = new HashSet<com.esori.list.models.Song>();
        int totalSongs;

        out.println("Please enter the playlist's id you wish to update");
        int id = Integer.parseInt(readLine(in));
        playlist.setId(id);

        out.println("New name of playlist?");
        playlist.setPlaylistName(readLine(in));
        out.println("New description:");
        playlist.setDescription(readLine(in));
        out.println("New duration of playlist?");
        playlist.setDuration(Integer.parseInt(readLine(in)));
        out.println("Total songs in playlist?");
        playlist.setNSongs(Integer.parseInt(readLine(in)));

        out.println("How many songs do you want to add to your playlist");
        totalSongs = Integer.parseInt(readLine(in));

        playlist.setNSongs(playlist.getNSongs()+totalSongs);
        insertSongs(totalSongs, songs);


        out.println("How many user do you wish to add to the playlist?");
        int totalUsers = Integer.parseInt(readLine(in));
        insertUsers(totalUsers, users);

        playlist.setSong(songs);
        playlist.setUser(users);

        playlistRepository.save(playlist);
    }

    private void delete() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var playlist = modelFactory.createPlaylist();
        var users = new HashSet<com.esori.list.models.User>();
        var songs = new HashSet<com.esori.list.models.Song>();

        out.println("Please enter the id of the playlist you would like to delete");
        int id =Integer.parseInt(readLine(in));
        playlist.setId(id);

        /*int deleteSel = deleteOptions();

        switch (deleteSel){
            case 2:
                out.println("How many songs do you want to delete?");
                int totalSongs = Integer.parseInt(readLine(in));
                for(int i = 0; i<totalSongs; i++){
                    out.println("Please enter the id of the song you would like to delete");
                    var song = (Song) Class.forName(properties.getProperty("song")).getConstructor().newInstance();
                    song.setId(Integer.parseInt(readLine(in)));
                    songs.add(song);
                }
                playlist.setSong(songs);
                break;
            case 3:
                out.println("How many users do you want to delete?");
                int totalUsers = Integer.parseInt(readLine(in));
                for(int i = 0; i<totalUsers; i++){
                    out.println("Please enter the id of the user you would like to delete");
                    var user = (User) Class.forName(properties.getProperty("user")).getConstructor().newInstance();
                    user.setId(Integer.parseInt(readLine(in)));
                    users.add(user);
                }
                playlist.setUser(users);
                break;
            default:
                out.println("Invalid command");
                break;
        }*/


        playlistRepository.delete(playlist);
    }

    private int deleteOptions() {

        out.println("Do you want to...");
        out.println("1 Delete the whole playlist");
        out.println("2 Delete a song from the playlist");
        out.println("3 Delete a user from the playlist");

        int deleteSel = Integer.parseInt(readLine(in));
        return deleteSel;
    }

    private void get(){
        var asciiTable = new AsciiTable();

        asciiTable.addRule();
        asciiTable.addRow("Id", "Playlist Name", "Description", "Total songs", "Duration");
        asciiTable.addRule();

        out.println("Please enter the playlist id you wish to search");
        int id = Integer.parseInt(readLine(in));
        var playlist = playlistRepository.get(id);
        asciiTable.addRow(playlist.getId(),playlist.getPlaylistName(), playlist.getDescription(),
                playlist.getNSongs(), playlist.getDuration());
        asciiTable.addRule();

        getTable(asciiTable);
    }

    private void getAll(){
        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "Playlist Name", "Description", "Total songs", "Duration");
        asciiTable.addRule();

        for(var playlist: playlistRepository.getAll()){
            asciiTable.addRow(playlist.getId(),playlist.getPlaylistName(), playlist.getDescription(),
                    playlist.getNSongs(), playlist.getDuration());
            asciiTable.addRule();
        }
        getTable(asciiTable);
    }
    private static void getTable(AsciiTable asciiTable) {
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        String render = asciiTable.render();
        System.out.println(render);
    }
}

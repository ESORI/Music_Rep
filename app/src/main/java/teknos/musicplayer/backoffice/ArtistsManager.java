package teknos.musicplayer.backoffice;

import com.esori.list.models.*;
import com.esori.list.repositories.ArtistRepository;
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

public class ArtistsManager {

    private final PrintStream out;
    private final BufferedReader in;
    private final ArtistRepository artistRepository;
    private final ModelFactory modelFactory;
    private final Properties properties = new Properties();

    public ArtistsManager(BufferedReader in, PrintStream out, ArtistRepository artistRepository, ModelFactory modelFactory) throws IOException {
        this.out = out;
        this.in = in;
        this.artistRepository = artistRepository;
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
        out.println("***Artists Manager***");
        out.println("Type:");
        out.println("1 to insert a new Artist");
        out.println("2 to update Artist");
        out.println("3 to delete Artist");
        out.println("4 to get an Artist");
        out.println("5 to show all Artists");
        out.println("'exit' to exit");
    }

    private void delete(){
        var artist = modelFactory.createArtist();

        out.println("Please enter the id of the artist you would like to delete");
        int id = Integer.parseInt(readLine(in));
        artist.setId(id);
        artistRepository.delete(artist);

    }

    private void insert() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var artist = modelFactory.createArtist();
        var artistData = (ArtistData)Class.forName(properties.getProperty("artistData")).getConstructor().newInstance();
        var albums = new HashSet<Album>();
        var songs = new HashSet<Song>();

        out.println("Artist or group name: ");
        artist.setGroupName(readLine(in));
        out.println("Monthly listeners: ");
        artist.setMonthlyList(Integer.parseInt(readLine(in)));

        out.println("Do you want to add Data to the Artist? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("Country:");
            artistData.setCountry(readLine(in));
            out.println("Debut Year:");
            artistData.setDebutYear(Integer.parseInt(readLine(in)));
            out.println("Language:");
            artistData.setLang(readLine(in));

            artist.setArtistData(artistData);
        }

        out.println("Does the artist have any album? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            insertAlbum(songs, albums, artist);
        }

        artistRepository.save(artist);

        out.println("Inserted artist succesfully: "+ artist);
    }

    private void insertAlbum(HashSet<com.esori.list.models.Song> songs, HashSet<com.esori.list.models.Album> albums, Artist artist) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        out.println("Total albums you want to add: :");
        int totalAlbums = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalAlbums; i++) {
            var album = (Album)Class.forName(properties.getProperty("album")).getConstructor().newInstance();
            album.setArtist(artist);
            out.println("Album name: ");
            album.setAlbumName(readLine(in));
            out.println("Total songs in album: ");
            album.setNSongs(Integer.parseInt(readLine(in)));
            out.println("Do you want to add songs into the Album?(yes/no): ");
            if(readLine(in).equalsIgnoreCase("yes")){
                insertSongs(songs, album, artist);
            }

            albums.add(album);
        }
        artist.setAlbum(albums);
    }

    private void insertSongs(HashSet<Song> songs, Album album, Artist artist)throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException  {
        out.println("Total songs in album you want to add: ");
        int totalSongs = Integer.parseInt(readLine(in));
        for (int j = 0; j < totalSongs; j++) {
            var song = (Song)Class.forName(properties.getProperty("song")).getConstructor().newInstance();
            song.setArtist(artist);
            song.setAlbum(album);
            out.println("Song name: ");
            song.setSongName(readLine(in));
            out.println("Song duration (sec): ");
            song.setDuration(Integer.parseInt(readLine(in)));
            songs.add(song);
        }
        album.setSong(songs);
    }

    private void update() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var artist = modelFactory.createArtist();
        var artistData = (ArtistData)Class.forName(properties.getProperty("artistData")).getConstructor().newInstance();
        var albums = new HashSet<com.esori.list.models.Album>();
        var songs = new HashSet<com.esori.list.models.Song>();

        out.println("Please enter the artist's id you wish to update");
        int id = Integer.parseInt(readLine(in));
        artist.setId(id);
        artistData.setId(id);


        out.println("New Group Name:");
        artist.setGroupName(readLine(in));
        out.println("Monthly listeners:");
        artist.setMonthlyList(Integer.parseInt(readLine(in)));

        out.println("Do you want to update the artist's Data? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("New Country:");
            artistData.setCountry(readLine(in));
            out.println("Debut Year:");
            artistData.setDebutYear(Integer.parseInt(readLine(in)));
            out.println("New language:");
            artistData.setLang(readLine(in));
            artist.setArtistData(artistData);
        }

        out.println("Do you want to update any Album? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("Please enter the album's id you wish to update\n0 if it's a new album (only works on jdbc for now)");
            int albumId = Integer.parseInt(readLine(in));
            if(albumId>0){
                updateAlbum(albumId, songs, albums, artist);
            }else{
                insertAlbum(songs, albums, artist);
            }
        }

        artistRepository.save(artist);
    }

    private void updateAlbum(int albumId, HashSet<Song> songs, HashSet<Album> albums, Artist artist) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var album = (Album) Class.forName(properties.getProperty("album")).getConstructor().newInstance();
        album.setId(albumId);
        out.println("New Album name:");
        album.setAlbumName(readLine(in));
        out.println("How many songs?");
        album.setNSongs(Integer.parseInt(readLine(in)));
        album.setArtist(artist);

        out.println("Do you want to update any song in the album? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            updateSong(songs, album, artist);
        }

        albums.add(album);
        artist.setAlbum(albums);
    }

    private void updateSong(HashSet<com.esori.list.models.Song> songs, Album album, Artist artist) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        out.println("Please enter the song's id you wish to update");
        int songId = Integer.parseInt(readLine(in));
        var song = (Song) Class.forName(properties.getProperty("song")).getConstructor().newInstance();
        song.setId(songId);
        out.println("New song name:");
        song.setSongName(readLine(in));
        out.println("New song duration (sec):");
        song.setDuration(Integer.parseInt(readLine(in)));
        song.setAlbum(album);
        song.setArtist(artist);
        songs.add(song);
        album.setSong(songs);
    }

    private void getAll(){
        var asciiTable = new AsciiTable();
        asciiTable.addRule();
        asciiTable.addRow("Id", "Group Name", "Monthly Listeners");
        asciiTable.addRule();

        for(var artist:artistRepository.getAll()){
            asciiTable.addRow(artist.getId(), artist.getGroupName(), artist.getMonthlyList());
            asciiTable.addRule();
        }

        getTable(asciiTable);
    }

    private void get(){

        out.println("Please enter the artist id you wish to search");
        int id = Integer.parseInt(readLine(in));
        var artist = artistRepository.get(id);

        var asciiTable = new AsciiTable();

        if(artist.getArtistData()!=null)
        asciiTable.addRule();
        asciiTable.addRow("Group name", "Monthly Listeners", "Country", "Language" ,"Debut Year");
        asciiTable.addRule();

        String country;
        String lang;
        int dy;
        if(artist.getArtistData()!=null){
            country = artist.getArtistData().getCountry();
            lang = artist.getArtistData().getLang();
            dy = artist.getArtistData().getDebutYear();
        }else{
            country = "No data";
            lang = "No data";
            dy = 0;
        }

        asciiTable.addRow(artist.getGroupName(), artist.getMonthlyList(),
                country, lang, dy);
        asciiTable.addRule();

        getTable(asciiTable);

        if(artist.getAlbum()!=null){
            var asciiTable2 = new AsciiTable();
            var asciiTable3 = new AsciiTable();

            asciiTable2.addRule();
            asciiTable2.addRow("Album Name", "Total Songs");
            asciiTable2.addRule();

            asciiTable3.addRule();
            asciiTable3.addRow("Album name","Song Name", "Duration");
            asciiTable3.addRule();

            if(artist.getAlbum()!=null){
                for(var album:artist.getAlbum()){
                    asciiTable2.addRow(album.getAlbumName(), album.getNSongs());
                    asciiTable2.addRule();
                    if(album.getSong()!=null){
                        for(var song:album.getSong()){
                            asciiTable3.addRow(album.getAlbumName(), song.getSongName(), song.getDuration());
                            asciiTable3.addRule();
                        }
                    }
                }
            }

            getTable(asciiTable2);
            getTable(asciiTable3);
        }


    }




    private static void getTable(AsciiTable asciiTable) {
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        asciiTable.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        String render = asciiTable.render();
        System.out.println(render);
    }
}
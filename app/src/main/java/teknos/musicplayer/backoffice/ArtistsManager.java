package teknos.musicplayer.backoffice;

import cat.uvic.teknos.musicrep.domain.jdbc.models.Album;
import cat.uvic.teknos.musicrep.domain.jdbc.models.ArtistData;
import cat.uvic.teknos.musicrep.domain.jdbc.models.Song;
import com.esori.list.models.Artist;
import com.esori.list.models.ModelFactory;
import com.esori.list.repositories.ArtistRepository;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.HashSet;

import static teknos.musicplayer.backoffice.IOUtils.readLine;

public class ArtistsManager {

    private final PrintStream out;
    private final BufferedReader in;
    private final ArtistRepository artistRepository;
    private final ModelFactory modelFactory;

    public ArtistsManager(BufferedReader in, PrintStream out, ArtistRepository artistRepository, ModelFactory modelFactory) {
        this.out = out;
        this.in = in;
        this.artistRepository = artistRepository;
        this.modelFactory = modelFactory;
    }

    public void start(){

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
                default -> out.println("Invalid command");
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

    private void insert() {
        var artist = modelFactory.createArtist();
        var artistData = new ArtistData();
        var albums = new HashSet<com.esori.list.models.Album>();
        var songs = new HashSet<com.esori.list.models.Song>();

        out.println("Artist or group name: ");
        artist.setGroupName(readLine(in));
        out.println("Monthly listeners: ");
        artist.setMonthlyList(Integer.parseInt(readLine(in)));

        out.println("Do you want to... add Data to the Artist? (yes/no)");
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

    private void insertAlbum(HashSet<com.esori.list.models.Song> songs, HashSet<com.esori.list.models.Album> albums, Artist artist) {
        out.println("Total albums you want to add: :");
        int totalAlbums = Integer.parseInt(readLine(in));
        for (int i = 0; i < totalAlbums; i++) {
            var album = new Album();
            out.println("Album name: ");
            album.setAlbumName(readLine(in));
            out.println("Total songs in album: ");
            album.setNSongs(Integer.parseInt(readLine(in)));
            out.println("Do you want to add songs into the Album?: ");
            if(readLine(in).equalsIgnoreCase("yes")){
                insertSongs(songs, album);
            }

            albums.add(album);
        }
        artist.setAlbum(albums);
    }

    private void insertSongs(HashSet<com.esori.list.models.Song> songs, Album album) {
        out.println("Total songs in album you want to add: ");
        int totalSongs = Integer.parseInt(readLine(in));
        for (int j = 0; j < totalSongs; j++) {
            var song = new Song();
            out.println("Song name: ");
            song.setSongName(readLine(in));
            out.println("Song duration (sec): ");
            song.setDuration(Integer.parseInt(readLine(in)));
            songs.add(song);
        }
        album.setSong(songs);
    }

    private void update(){
        var artist = modelFactory.createArtist();
        var artistData = new ArtistData();
        var albums = new HashSet<com.esori.list.models.Album>();
        var songs = new HashSet<com.esori.list.models.Song>();

        out.println("Please enter the artist's id you wish to update");
        int id = Integer.parseInt(readLine(in));
        artist.setId(id);
        artistData.setId(id);


        out.println("Do you want to update the artist's name? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("New Group Name:");
            artist.setGroupName(readLine(in));
        }

        out.println("Do you want to update the artist's country? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("New Country:");
            artistData.setCountry(readLine(in));
            artist.setArtistData(artistData);
        }

        out.println("Do you want to update any Album? (yes/no");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("Please enter the album's id you wish to update (0 if it's a new album)");
            int albumId = Integer.parseInt(readLine(in));
            if(albumId>0){
                updateAlbum(albumId, songs, albums, artist);
            }else{
                insertAlbum(songs, albums, artist);
            }

        }

        artistRepository.save(artist);
    }

    private void updateAlbum(int albumId, HashSet<com.esori.list.models.Song> songs, HashSet<com.esori.list.models.Album> albums, Artist artist) {
        var album = new Album();
        album.setId(albumId);
        out.println("Do you want to update the album's name? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            out.println("New Album name:");
            album.setAlbumName(readLine(in));
        }

        out.println("Do you want to update any song in the album? (yes/no)");
        if(readLine(in).equalsIgnoreCase("yes")){
            updateSong(songs, album);
        }

        albums.add(album);
        artist.setAlbum(albums);
    }

    private void updateSong(HashSet<com.esori.list.models.Song> songs, Album album) {
        out.println("Please enter the song's id you wish to update");
        int songId = Integer.parseInt(readLine(in));
        var song = new Song();
        song.setId(songId);
        out.println("New song name:");
        song.setSongName(readLine(in));
        out.println("New song duration (sec):");
        song.setDuration(Integer.parseInt(readLine(in)));
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
        
        asciiTable.addRule();
        asciiTable.addRow("Group name", "Monthly Listeners", "Country", "Language" ,"Debut Year");
        asciiTable.addRule();

        asciiTable.addRow(artist.getGroupName(), artist.getMonthlyList(),
                artist.getArtistData().getCountry(), artist.getArtistData().getLang(), artist.getArtistData().getDebutYear());
        asciiTable.addRule();


        var asciiTable2 = new AsciiTable();
        var asciiTable3 = new AsciiTable();

        asciiTable2.addRule();
        asciiTable2.addRow("Album Name", "Total Songs");
        asciiTable2.addRule();

        asciiTable3.addRule();
        asciiTable3.addRow("Album name","Song Name", "Duration");
        asciiTable3.addRule();

        for(var album:artist.getAlbum()){
            asciiTable2.addRow(album.getAlbumName(), album.getNSongs());
            asciiTable2.addRule();
            for(var song:album.getSong()){
                asciiTable3.addRow(album.getAlbumName(), song.getSongName(), song.getDuration());
                asciiTable3.addRule();
            }
        }

        getTable(asciiTable);
        getTable(asciiTable2);
        getTable(asciiTable3);

    }




    private static void getTable(AsciiTable asciiTable) {
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String render = asciiTable.render();
        System.out.println(render);
    }
}

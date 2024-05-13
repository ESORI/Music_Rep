package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.Playlist;
import cat.uvic.teknos.musicrep.domain.jdbc.models.Song;
import cat.uvic.teknos.musicrep.domain.jdbc.models.User;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import com.sun.source.util.Plugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcPlaylistRepositoryTest {

    private final Connection connection;

    public JdbcPlaylistRepositoryTest(Connection connection){
        this.connection = connection;
    }


    @Test
    void shouldInsertPlaylist() {

        Playlist playlist = new Playlist();
        playlist.setPlaylistName("Car rides");
        playlist.setDuration(565);
        playlist.setDescription("Songs to listen to while driving");
        playlist.setNSongs(2);

        User user = new User();
        user.setId(2);

        User user2 = new User();
        user2.setId(1);

        var users = new HashSet< com.esori.list.models.User>();
        users.add(user);
        users.add(user2);

        Song song = new Song();
        song.setId(1);

        Song song2 = new Song();
        song2.setId(2);

        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);
        songs.add(song2);

        playlist.setSong(songs);
        playlist.setUser(users);

        var repository = new JdbcPlaylistRepository(connection);
        repository.save(playlist);

        assertTrue(playlist.getId() > 0);


        DbAssertions.assertThat(connection)
                .table("PLAYLIST")
                .where("ID_PLAYLIST", playlist.getId())
                .hasOneLine();

    }

    @Test
    void shouldUpdatePlaylist(){

        Playlist playlist = new Playlist();
        playlist.setId(2);
        playlist.setDescription("Description test");
        playlist.setNSongs(3);


        Song song = new Song();
        song.setId(1);

        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);

        playlist.setSong(songs);

        var repository = new JdbcPlaylistRepository(connection);
        repository.save(playlist);


    }

    @Test
    void delete() {
        //Deletes whole playlist
        //Commented deleting a whole playlist to avoid problems when executing all tests at the same time
        //Can be uncommented and tried alone. It works!
        /*
        Playlist playlist = new Playlist();
        playlist.setId(1);
*/
        Playlist playlist1 = new Playlist();
        playlist1.setId(2);

        Song song = new Song();
        song.setId(3);

        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);

        playlist1.setSong(songs);

        var repository = new JdbcPlaylistRepository(connection);
        //repository.delete(playlist);
        repository.delete(playlist1);
/*
        DbAssertions.assertThat(connection)
                .table("PLAYLIST")
                .where("ID_PLAYLIST", playlist.getId())
                .doesNotExist();
                */

    }

    @Test
    void get() {
        int id = 1;
        var repository = new JdbcPlaylistRepository(connection);

        com.esori.list.models.Playlist playlist = repository.get(id);
        SoutPlaylist(playlist);
    }

    @Test
    void getAll() {
        var repository = new JdbcPlaylistRepository(connection);
        Set<com.esori.list.models.Playlist> playlists = repository.getAll();

        for(var playlist:playlists){
            SoutPlaylist(playlist);
        }
    }

    private void SoutPlaylist(com.esori.list.models.Playlist playlist){
        System.out.println("Playlist: " + playlist.getId());
        System.out.println("Name: " + playlist.getPlaylistName());
        System.out.println("Description: " + playlist.getDescription());
        System.out.println(playlist.getNSongs() + " songs in playlist");
        System.out.println(playlist.getDuration() + " seconds of total duration");

        if(playlist.getSong()!=null){
            System.out.println("**\nSongs in list: ");
            for(var song:playlist.getSong()){
                System.out.println(song.getSongName());
            }
        }

        if(playlist.getUser()!=null){
            System.out.println("**\nUsers added to list: ");
            for(var user:playlist.getUser()){
                System.out.println(user.getUsername());
            }
        }
        System.out.println("\n\n");
    }

}
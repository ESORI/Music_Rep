package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.Playlist;
import cat.uvic.teknos.musicrep.domain.jdbc.models.Song;
import cat.uvic.teknos.musicrep.domain.jdbc.models.User;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcPlaylistRepositoryTest {
    /*


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
    void delete() {
    }

    @Test
    void get() {
    }

    @Test
    void getAll() {
    }
*/

}
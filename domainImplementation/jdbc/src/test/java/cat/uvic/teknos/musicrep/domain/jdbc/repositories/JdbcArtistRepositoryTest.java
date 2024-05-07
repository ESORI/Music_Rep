package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.*;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcArtistRepositoryTest {


    private final Connection connection;

    public JdbcArtistRepositoryTest(Connection connection){
        this.connection = connection;
    }


    @Test
    void shouldInsertArtist() {

        Artist artist = new Artist();
        artist.setGroupName("Bring me the horizon");
        artist.setMonthlyList(48000);

        Album album = new Album();
        album.setAlbumName("Sempiternal");
        album.setNSongs(13);

        Song song = new Song();
        song.setSongName("Can you feel my heart");
        song.setDuration(229);

        Song song2 = new Song();
        song2.setSongName("Empire");
        song2.setDuration(226);
/*
        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);
        songs.add(song2);

        album.setSong(songs);
*/
        var albums = new HashSet<com.esori.list.models.Album>();
        albums.add(album);

        artist.setAlbum(albums);



        var repository = new JdbcArtistRepository(connection);

        // Test
        repository.save(artist);

        assertTrue(artist.getId() > 0);


        DbAssertions.assertThat(connection)
                .table("ARTIST")
                .where("ID_ARTIST", artist.getId())
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


}
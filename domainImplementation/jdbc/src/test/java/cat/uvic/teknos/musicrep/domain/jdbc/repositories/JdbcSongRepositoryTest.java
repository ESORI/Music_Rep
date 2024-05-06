package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.Album;
import cat.uvic.teknos.musicrep.domain.jdbc.models.Song;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({CreateSchemaExtension.class, GetConnectionExtension.class})
class JdbcSongRepositoryTest {
    /*

    private final Connection connection;

    public JdbcSongRepositoryTest(Connection connection){
        this.connection = connection;
    }


    @Test
    @DisplayName("Given a new song Name (id=?), when save, then a new record is added to the SONG table (In this test specifically to the first artist with its first album)")
    void shouldInsertNewSongNameTest() {
//CHANGE TO ADD ALBUM SINCE WE'RE INSERTING IT AS WELL IN THIS REPOSITORY
        Song song = new Song();
        song.setSongName("Breaking the habit");

        var repository = new JdbcSongRepository(connection);

        //Test
        repository.save(song);

        assertTrue(song.getId()>0);

        assertNotNull(repository.get(song.getId()));

        DbAssertions.assertThat(connection)
                .table("SONG").where("ID_SONG", song.getId()).hasOneLine();
    }

    @Test
    void shouldUpdateSongName() {
        Song song = new Song();
        song.setId(3);
        song.setSongName("New Born, Radio Edit");

        var repository = new JdbcSongRepository(connection);


        repository.save(song);

        //TODO: test database table updated
        assertTrue(song.getId()>0);

    }


    @Test
    void shouldDelete() {

        Song song = new Song();
        song.setId(7);

        var repository = new JdbcSongRepository(connection);
        repository.delete(song);

        //TODO: test database table updated
        //assertTrue(true);

        DbAssertions.assertThat(connection)
                .table("SONG").where("ID_SONG", song.getId()).doesNotExist();
    }

    @Test
    void get() {
        /*
        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost/music_rep", "root", null)){
            Song song = new Song();
            song.setId(4);

            var repository = new JdbcSongRepository(connection);
            repository.get(song.getId());


            //TODO: test database table updated
            assertTrue(true);
        }

        var repository = new JdbcSongRepository(connection);
        assertNotNull(repository.get(1));


    }

    @Test
    void getAll() {
    }
*/
}
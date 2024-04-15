package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.Song;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSongRepositoryTest {

    @Test
    @DisplayName("Given a new song Name (id=?), when save, then a new record is added to the SONG table (In this test specifically to the first artist with its first album)")
    void shouldInsertNewSongNameTest() throws SQLException {

        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost/music_rep", "root", null)){
            Song song = new Song();
            song.setSongName("Breaking the habit");

            var repository = new JdbcSongRepository(connection);
            repository.save(song);

            assertTrue(song.getId()>0);
        }
    }

    @Test
    void shouldUpdateSongName() throws SQLException {
        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost/music_rep", "root", null)){
            Song song = new Song();
            song.setId(3);
            song.setSongName("New Born, Radio Edit");

            var repository = new JdbcSongRepository(connection);
            repository.save(song);

            //TODO: test database table updated
            assertTrue(true);
        }
    }


    @Test
    void shouldDelete() throws SQLException {
        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost/music_rep", "root", null)){
            Song song = new Song();
            song.setId(7);

            var repository = new JdbcSongRepository(connection);
            repository.delete(song);

            //TODO: test database table updated
            assertTrue(true);
        }
    }

    @Test
    void get() throws SQLException {
        try(var connection = DriverManager.getConnection("jdbc:mysql://localhost/music_rep", "root", null)){
            Song song = new Song();
            song.setId(4);

            var repository = new JdbcSongRepository(connection);
            repository.get(song.getId());


            //TODO: test database table updated
            assertTrue(true);
        }
    }

    @Test
    void getAll() {
    }
}
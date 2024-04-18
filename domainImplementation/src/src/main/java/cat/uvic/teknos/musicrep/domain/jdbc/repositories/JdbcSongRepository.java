package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Album;
import com.esori.list.models.Artist;
import com.esori.list.models.Song;
import com.esori.list.repositories.SongRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class JdbcSongRepository implements SongRepository {

    private static final String INSERT_SONG = "INSERT INTO SONG (ID_ALBUM, ID_ARTIST, SONG_NAME, DURATION) VALUES (?,?,?,?)";
    private static final String INSERT_ALBUM = "INSERT INTO ALBUM (ID_ARTIST, ALBUM_NAME, N_SONG) VALUES (?,?,?)";
    private final Connection connection;

    public JdbcSongRepository(Connection connection){
        this.connection = connection;
    }

    @Override
    public void save(Song model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(Song model) {
        try(var statement = connection.prepareStatement(INSERT_SONG,Statement.RETURN_GENERATED_KEYS);
            var albumSong = connection.prepareStatement(INSERT_ALBUM)){
            statement.setInt(1,1);
            statement.setInt(2,1);
            statement.setString(3, model.getSongName());
            statement.setInt(4,215);

            connection.setAutoCommit(false);

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();

            if(keys.next()){
                model.setId(keys.getInt(1));
            }

            albumSong.setInt(1, model.getId());
            albumSong.setString(2, model.getAlbum().getAlbumName());
            albumSong.setInt(3,model.getAlbum().getNSongs());

            connection.commit();

        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    private void setAutoCommitTrue() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void rollback() {
        try{
            connection.rollback();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    private void update(Song model) {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE SONG SET SONG_NAME= ? WHERE ID_SONG = ?")){
            statement.setString(1, model.getSongName());
            statement.setInt(2,model.getId());

            connection.setAutoCommit(false);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Song model) {
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM SONG WHERE ID_SONG = ?");
            PreparedStatement albumStatement = connection.prepareStatement("DELETE FROM ALBUM WHERE ID = ?")){
            connection.setAutoCommit(false);

            statement.setInt(1,model.getId());
            albumStatement.setInt(1, model.getAlbum().getId());

            statement.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        }finally{
            setAutoCommitTrue();
        }
    }

    @Override
    public Song get(Integer id) {
        //String data = "";
        //Song song = new cat.uvic.teknos.musicrep.domain.jdbc.models.Song();

        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM SONG WHERE ID_SONG = ?");
            PreparedStatement albumStatement = connection.prepareStatement("SELECT * FROM ALBUM WHERE ID = ?")){
            Song song = null;
            statement.setInt(1,id);

            var resultSet = statement.executeQuery();
            if(resultSet.next()){
                song = new cat.uvic.teknos.musicrep.domain.jdbc.models.Song();
                song.setId(resultSet.getInt("ID_SONG"));
                //song.setAlbum(albumStatement.setInt(resultSet.getInt("ID_ALBUM")));
                //song.setArtist((Artist) resultSet.getObject("ID_ARTIST"));
                song.setSongName(resultSet.getString("SONG_NAME"));
                song.setDuration(resultSet.getInt("DURATION"));


            }
            return song;
        } catch (SQLException e) {
            throw new RuntimeException("Song with id " + id + " not found");
        }
        //return song;
    }

    @Override
    public Set<Song> getAll() {
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM SONG WHERE ID_SONG = ?")){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Song song = new cat.uvic.teknos.musicrep.domain.jdbc.models.Song();
                song.setId(resultSet.getInt("ID_SONG"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

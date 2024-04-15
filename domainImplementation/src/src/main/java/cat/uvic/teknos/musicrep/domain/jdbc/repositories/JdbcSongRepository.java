package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Album;
import com.esori.list.models.Artist;
import com.esori.list.models.Song;
import com.esori.list.repositories.SongRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class JdbcSongRepository implements SongRepository {

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
        try(PreparedStatement statement = connection.prepareStatement("INSERT INTO SONG (ID_ALBUM, ID_ARTIST, SONG_NAME, DURATION) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){
            statement.setInt(1,1);
            statement.setInt(2,1);
            statement.setString(3, model.getSongName());
            statement.setInt(4,215);

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();

            if(keys.next()){
                model.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Song model) {
        try(PreparedStatement statement = connection.prepareStatement("UPDATE SONG SET SONG_NAME= ? WHERE ID_SONG = ?")){
            statement.setString(1, model.getSongName());
            statement.setInt(2,model.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Song model) {
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM SONG WHERE ID_SONG = ?")){
            statement.setInt(1,model.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Song get(Integer id) {
        String data = "";
        Song song = new cat.uvic.teknos.musicrep.domain.jdbc.models.Song();
        try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM SONG WHERE ID_SONG = ?")){
            statement.setInt(1,id);

            var resultSet = statement.executeQuery();

            while(resultSet.next()){

                song.setId(resultSet.getInt("ID_SONG"));
                song.setAlbum((Album) resultSet.getObject("ID_ALBUM"));
                song.setArtist((Artist) resultSet.getObject("ID_ARTIST"));
                song.setSongName(resultSet.getString("SONG_NAME"));
                song.setDuration(resultSet.getInt("DURATION"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return song;
    }

    @Override
    public Set<Song> getAll() {
        return null;
    }
}

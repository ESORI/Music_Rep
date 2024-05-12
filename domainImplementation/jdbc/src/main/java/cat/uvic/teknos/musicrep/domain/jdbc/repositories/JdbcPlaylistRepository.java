package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Playlist;
import com.esori.list.repositories.PlaylistRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;


//THIS REPOSITORY WILL HANDLE PLAYLIST, PLAYLIST_USER AND PLAYLIST_SONG
//DEPENDING ON HOW THE OTHER REPOSITORIES WORK THIS ONE WILL HAVE INSERT AND UPDATE OR A SIMPLE INSERT WITH DUPLICATE ON KEY UPDATE

public class JdbcPlaylistRepository implements PlaylistRepository {

    private static final String INSERT_PLAYLIST = "INSERT INTO PLAYLIST (QUANTITY_SONGS, PLAYLIST_NAME,DESCRIPTION,DURATION) VALUES (?,?,?,?)";
    private static final String INSERT_PLAYLIST_USER = "INSERT INTO PLAYLIST_USER (ID_PLAYLIST, ID_USER) VALUES (?,?)";
    private static final String INSERT_PLAYLIST_SONG = "INSERT INTO PLAYLIST_SONG (ID_PLAYLIST, ID_SONG) VALUES (?,?)";

    private final Connection connection;

    public JdbcPlaylistRepository(Connection connection){
        this.connection = connection;
    }


    @Override
    public void save(Playlist model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void insert(Playlist model){
        try(
                var preparedStatement = connection.prepareStatement(INSERT_PLAYLIST, Statement.RETURN_GENERATED_KEYS);
                var playlistUserStatement = connection.prepareStatement(INSERT_PLAYLIST_USER);
                var playlistSongStatement = connection.prepareStatement(INSERT_PLAYLIST_SONG)
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, model.getNSongs());
            preparedStatement.setString(2, model.getPlaylistName());
            preparedStatement.setString(3, model.getDescription());
            preparedStatement.setInt(4, model.getDuration());
            preparedStatement.executeUpdate();
            var keys = preparedStatement.getGeneratedKeys();
            if(keys.next()){
                model.setId(keys.getInt(1));
            }

            if(model.getSong()!=null){
                for(var song:model.getSong()){
                    playlistSongStatement.setInt(1, model.getId());
                    playlistSongStatement.setInt(2, song.getId());
                    playlistSongStatement.executeUpdate();
                }
            }

            if(model.getUser()!=null){
                for(var user:model.getUser()){
                    playlistUserStatement.setInt(1, model.getId());
                    playlistUserStatement.setInt(2, user.getId());
                    playlistUserStatement.executeUpdate();
                }
            }
            connection.commit();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void update(Playlist model){
        try(
                var preparedStatement = connection.prepareStatement("UPDATE PLAYLIST SET QUANTITY_SONGS = ?, PLAYLIST_NAME = ? WHERE ID_PLAYLIST = ?");
                var playlistUserStatement = connection.prepareStatement(("UPDATE PLAYLIST SET ID_USER = ? WHERE ID_PLAYLIST = ?"));
                var playlistSongStatement = connection.prepareStatement("UPDATE")
                ) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Playlist model) {

    }

    @Override
    public Playlist get(Integer id) {
        return null;
    }

    @Override
    public Set<Playlist> getAll() {
        return null;
    }
}

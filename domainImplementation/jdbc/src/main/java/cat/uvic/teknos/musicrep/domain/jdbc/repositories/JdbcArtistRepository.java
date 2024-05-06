package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.UserData;
import com.esori.list.models.Album;
import com.esori.list.models.Artist;
import com.esori.list.models.User;
import com.esori.list.repositories.ArtistRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


//will try to do this one with a (ON DUPLICATED KEY) when inserting, that way there will be no need to make an update method
public class JdbcArtistRepository implements ArtistRepository {

    private static final String INSERT_ARTIST = "INSERT INTO ARTIST (GROUP_NAME, MONTHLY_LIST) VALUES (?,?) ON DUPLICATE KEY UPDATE";
    private static final String INSERT_ALBUM = "INSERT INTO ALBUM (ID_ARTIST, ALBUM_NAME, N_SONGS) VALUES (?,?,?) ON DUPLICATE KEY UPDATE";
    private final Connection connection;

    public JdbcArtistRepository(Connection connection){
        this.connection = connection;
    }


    @Override
    public void save(Artist model) {
        try(var statement = connection.prepareStatement(INSERT_ARTIST,Statement.RETURN_GENERATED_KEYS);
            var albumStatement = connection.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,model.getGroupName());
            statement.setInt(2,model.getMonthlyList());

            connection.setAutoCommit(false);

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                model.setId(keys.getInt(1));
            }

            if(model.getAlbum()!=null){
                for(var album : model.getAlbum()){
                    albumStatement.setInt(1, model.getId());
                    albumStatement.setString(2, album.getAlbumName());
                    albumStatement.setInt(3, album.getNSongs());
                }
            }

            connection.commit();

        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    @Override
    public void delete(Artist model) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ARTIST where ID_ARTIST = ?");
            PreparedStatement userDataStatement = connection.prepareStatement("DELETE FROM ALBUM where ID_ARTIST = ?")) {

            connection.setAutoCommit(false);
            preparedStatement.setInt(1,model.getId());

            userDataStatement.setInt(1,model.getId());
            userDataStatement.executeUpdate();

            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            rollback();
            throw new RuntimeException(e);
        } finally {
            setAutoCommitTrue();
        }
    }

    @Override
    public Artist get(Integer id) {
        String query = "SELECT * FROM ARTIST WHERE ID_ARTIST = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Artist result = new cat.uvic.teknos.musicrep.domain.jdbc.models.Artist();
                    result.setId(resultSet.getInt("ID_ARTIST"));
                    result.setGroupName(resultSet.getString("GROUP_NAME"));
                    result.setMonthlyList(resultSet.getInt("MONTHLY_LIST"));

                    Album album = new cat.uvic.teknos.musicrep.domain.jdbc.models.Album();
                    album.setAlbumName(resultSet.getString("ALBUM_NAME"));
                    album.setNSongs(resultSet.getInt("N_SONGS"));

                    var albums = new HashSet<Album>();
                    albums.add(album);

                    result.setAlbum(albums);


                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Set<Artist> getAll() {
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USER")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Artist> artists = new HashSet<>();
            while (resultSet.next()) {
                Artist artist = new cat.uvic.teknos.musicrep.domain.jdbc.models.Artist();
                artist.setId(resultSet.getInt("ID_ARTIST"));
                artist.setGroupName(resultSet.getString("GROUP_NAME"));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}

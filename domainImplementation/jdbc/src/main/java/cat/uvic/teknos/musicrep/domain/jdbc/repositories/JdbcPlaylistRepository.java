package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.Playlist;
import com.esori.list.models.Song;
import com.esori.list.models.User;
import com.esori.list.repositories.PlaylistRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


//THIS REPOSITORY WILL HANDLE PLAYLIST, PLAYLIST_USER AND PLAYLIST_SONG
//DEPENDING ON HOW THE OTHER REPOSITORIES WORK THIS ONE WILL HAVE INSERT AND UPDATE OR A SIMPLE INSERT WITH DUPLICATE ON KEY UPDATE

public class JdbcPlaylistRepository implements PlaylistRepository {

    private static final String INSERT_PLAYLIST = "INSERT INTO PLAYLIST (QUANTITY_SONGS, PLAYLIST_NAME,DESCRIPTION,DURATION) VALUES (?,?,?,?)";
    private static final String INSERT_PLAYLIST_USER = "INSERT INTO PLAYLIST_USER (ID_PLAYLIST, ID_USER) VALUES (?,?)";
    private static final String INSERT_PLAYLIST_SONG = "INSERT INTO PLAYLIST_SONG (ID_PLAYLIST, ID_SONG) VALUES (?,?)";

    private final Connection connection;

    public JdbcPlaylistRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(Playlist model) {
        if (model.getId() <= 0) {
            insert(model);
        } else {
            update(model);
        }
    }

    private void insert(Playlist model) {
        try (
                var preparedStatement = connection.prepareStatement(INSERT_PLAYLIST, Statement.RETURN_GENERATED_KEYS);

        ) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, model.getNSongs());
            preparedStatement.setString(2, model.getPlaylistName());
            preparedStatement.setString(3, model.getDescription());
            preparedStatement.setInt(4, model.getDuration());
            preparedStatement.executeUpdate();
            var keys = preparedStatement.getGeneratedKeys();
            if (keys.next()) {
                model.setId(keys.getInt(1));
            }

            if (model.getSong() != null) {
                for (var song : model.getSong()) {
                    InsertSong(song, model.getId());
                }
            }

            if (model.getUser() != null) {
                for (var user : model.getUser()) {
                    InsertUser(user, model.getId());
                }
            }
            connection.commit();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void InsertUser(User model, int id) {
        try (
                var playlistUserStatement = connection.prepareStatement(INSERT_PLAYLIST_USER);
        ) {
            playlistUserStatement.setInt(1, id);
            playlistUserStatement.setInt(2, model.getId());
            playlistUserStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void InsertSong(Song model, int id) {
        try (
                var playlistSongStatement = connection.prepareStatement(INSERT_PLAYLIST_SONG)
        ) {
            playlistSongStatement.setInt(1, id);
            playlistSongStatement.setInt(2, model.getId());
            playlistSongStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //DID NOT UPDATE PLAYLIST USER AND PLAYLIST SONG SINCE I THINK IT DOESN'T MAKE ANY SENSE
    //WE EITHER INSERT A NEW VALUE OR DELETE IT, NOT UPDATE IT SINCE IT JUST REFERENCES WHO IT IS TARGETING
    //SO IF WE ADD A SONG/USER HERE IT WILL ONLY INSERT IT
    private void update(Playlist model) {
        try (
                var preparedStatement = connection.prepareStatement("UPDATE PLAYLIST SET QUANTITY_SONGS = ?, DESCRIPTION = ?, PLAYLIST_NAME = ?, DURATION = ? WHERE ID_PLAYLIST = ?");
        ) {
            connection.setAutoCommit(false);

            var id = model.getId();

            if (model.getDescription() != null) {
                preparedStatement.setInt(1, model.getNSongs());
                preparedStatement.setString(2, model.getDescription());
                preparedStatement.setString(3, model.getPlaylistName());
                preparedStatement.setInt(4, model.getDuration());
                preparedStatement.setInt(5, id);
                preparedStatement.executeUpdate();
            }

            if (model.getUser() != null) {
                for (var user : model.getUser()) {
                    InsertUser(user, id);
                }
            }

            if (model.getSong() != null) {
                for (var song : model.getSong()) {
                    InsertSong(song, model.getId());
                }
            }

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Playlist model) {
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PLAYLIST WHERE ID_PLAYLIST = ?");
                PreparedStatement userStatement = connection.prepareStatement("DELETE FROM PLAYLIST_USER WHERE ID_PLAYLIST = ? AND ID_USER = ?");
                PreparedStatement songStatement = connection.prepareStatement("DELETE FROM PLAYLIST_SONG WHERE ID_PLAYLIST = ? AND ID_SONG = ?")
        ) {
            int id = model.getId();
            connection.setAutoCommit(false);

            if (model.getUser() == null && model.getSong() == null) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }

            if (model.getUser() != null) {
                for (var user : model.getUser()) {
                    userStatement.setInt(1, id);
                    userStatement.setInt(2, user.getId());
                    userStatement.executeUpdate();
                }
            }

            if (model.getSong() != null) {
                for (var song : model.getSong()) {
                    songStatement.setInt(1, id);
                    songStatement.setInt(2, song.getId());
                    songStatement.executeUpdate();
                }
            }

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Playlist get(Integer id) {
        String query = "SELECT * FROM PLAYLIST WHERE ID_PLAYLIST = ?";
        String query1 = "SELECT SONG_NAME FROM SONG WHERE ID_SONG IN (SELECT ID_SONG FROM PLAYLIST_SONG WHERE ID_PLAYLIST = ?)";
        String query2 = "SELECT USERNAME FROM USER WHERE ID_USER IN (SELECT ID_USER FROM PLAYLIST_USER WHERE ID_PLAYLIST = ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(query);
                PreparedStatement songStatement = connection.prepareStatement(query1);
                PreparedStatement userStatement = connection.prepareStatement(query2)
        ) {
            statement.setInt(1, id);
            songStatement.setInt(1, id);
            userStatement.setInt(1, id);

            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Playlist result = new cat.uvic.teknos.musicrep.domain.jdbc.models.Playlist();
                    result.setId(resultSet.getInt("ID_PLAYLIST"));
                    result.setNSongs(resultSet.getInt("QUANTITY_SONGS"));
                    result.setDescription(resultSet.getString("DESCRIPTION"));
                    result.setPlaylistName(resultSet.getString("PLAYLIST_NAME"));
                    result.setDuration(resultSet.getInt("DURATION"));

                    var users = new HashSet<User>();
                    var resultSetUser = userStatement.executeQuery();

                    while (resultSetUser.next()) {
                        User resultUser = new cat.uvic.teknos.musicrep.domain.jdbc.models.User();
                        resultUser.setUsername(resultSetUser.getString("USERNAME"));
                        users.add(resultUser);
                    }
                    result.setUser(users);

                    var songs = new HashSet<Song>();
                    var resultSetSong = songStatement.executeQuery();

                    while (resultSetSong.next()) {
                        Song resultSong = new cat.uvic.teknos.musicrep.domain.jdbc.models.Song();
                        resultSong.setSongName(resultSetSong.getString("SONG_NAME"));
                        songs.add(resultSong);
                    }

                    result.setSong(songs);

                    return result;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Set<Playlist> getAll() {
        String query = "SELECT * FROM PLAYLIST";
        String query1 = "SELECT SONG_NAME FROM SONG WHERE ID_SONG IN (SELECT ID_SONG FROM PLAYLIST_SONG WHERE ID_PLAYLIST = ?)";
        String query2 = "SELECT USERNAME FROM USER WHERE ID_USER IN (SELECT ID_USER FROM PLAYLIST_USER WHERE ID_PLAYLIST = ?)";

        try (
                PreparedStatement statement = connection.prepareStatement(query);
                PreparedStatement songStatement = connection.prepareStatement(query1);
                PreparedStatement userStatement = connection.prepareStatement(query2)
        ) {
            var playlists = new HashSet<Playlist>();

            var resultSet = statement.executeQuery();


            int id = 1;
            while (resultSet.next()) {

                songStatement.setInt(1, id);
                userStatement.setInt(1, id);
                id++;


                Playlist result = new cat.uvic.teknos.musicrep.domain.jdbc.models.Playlist();
                result.setId(resultSet.getInt("ID_PLAYLIST"));
                result.setNSongs(resultSet.getInt("QUANTITY_SONGS"));
                result.setDescription(resultSet.getString("DESCRIPTION"));
                result.setPlaylistName(resultSet.getString("PLAYLIST_NAME"));
                result.setDuration(resultSet.getInt("DURATION"));

                var users = new HashSet<User>();
                var resultSetUser = userStatement.executeQuery();

                while (resultSetUser.next()) {
                    User resultUser = new cat.uvic.teknos.musicrep.domain.jdbc.models.User();
                    resultUser.setUsername(resultSetUser.getString("USERNAME"));
                    users.add(resultUser);
                }
                result.setUser(users);

                var songs = new HashSet<Song>();
                var resultSetSong = songStatement.executeQuery();

                while (resultSetSong.next()) {
                    Song resultSong = new cat.uvic.teknos.musicrep.domain.jdbc.models.Song();
                    resultSong.setSongName(resultSetSong.getString("SONG_NAME"));
                    songs.add(resultSong);
                }

                result.setSong(songs);

                playlists.add(result);
            }
            return playlists;



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

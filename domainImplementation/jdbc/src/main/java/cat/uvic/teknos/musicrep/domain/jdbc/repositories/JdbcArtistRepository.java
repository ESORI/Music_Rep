package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.models.*;
import com.esori.list.repositories.ArtistRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;


//will try to do this one with a (ON DUPLICATED KEY) when inserting, that way there will be no need to make an update method
public class JdbcArtistRepository implements ArtistRepository {

    private static final String INSERT_ARTIST = "INSERT INTO ARTIST (GROUP_NAME, MONTHLY_LIST) VALUES (?,?)";
    private static final String INSERT_ALBUM = "INSERT INTO ALBUM (ID_ARTIST, ALBUM_NAME, QUANTITY_SONGS) VALUES (?,?,?)";
    private static final String INSERT_SONG = "INSERT INTO SONG (ID_ALBUM, ID_ARTIST, SONG_NAME, DURATION) VALUES (?,?,?,?)";
    private static final String INSERT_ARTIST_DATA = "INSERT INTO ARTIST_DATA (ID_ARTIST, COUNTRY, LANG, DEBUT_YEAR) VALUES (?,?,?,?)";
    private final Connection connection;

    public JdbcArtistRepository(Connection connection){
        this.connection = connection;
    }


    //SAVES FOR EVERY TABLE: ARTIST, ARTIST DATA, ALBUM AND SONG
    @Override
    public void save(Artist model) {
        if(model.getId()<=0){
            insert(model);
        }else{
            update(model);
        }
    }

    private void saveArtistData(ArtistData model, int id){
        if(model.getId()<=0){
            insertArtistData(model, id);
        }else{
            updateArtistData(model,id);
        }
    }


    private void saveAlbum(Album model, int id){
        if(model.getId()<=0){
            insertAlbum(model, id);
        }else{
            updateAlbum(model,id);
        }
    }


    private void saveSong(Song model, int idArtist, int idAlbum){
        if(model.getId()<=0){
            insertSong(model, idArtist, idAlbum);
        }else{
            updateSong(model, idAlbum);
        }
    }



    //INSERT AND UPDATE FOR ARTIST

    private void insert(Artist model) {
        try(var statement = connection.prepareStatement(INSERT_ARTIST,Statement.RETURN_GENERATED_KEYS);
            var albumStatement = connection.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS);
            var songStatement = connection.prepareStatement(INSERT_SONG, Statement.RETURN_GENERATED_KEYS)
            ){


            connection.setAutoCommit(false);


            statement.setString(1, model.getGroupName());
            statement.setInt(2, model.getMonthlyList());

            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                model.setId(keys.getInt(1));
            }

            //INSERT TO OTHER TABLES
            int id = model.getId();

            //INSERT TO ARTIST DATA
            if(model.getArtistData()!=null){
                saveArtistData(model.getArtistData(), id);
            }

            //INSERT TO ALBUM AND SONG
            if(model.getAlbum()!=null){
                for(var album : model.getAlbum()){
                    saveAlbum(album, id);
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

    private void update(Artist model){
        try(
                var preparedStatement = connection.prepareStatement("UPDATE ARTIST SET GROUP_NAME = ?, MONTHLY_LIST = ? WHERE ID_ARTIST = ?");
                ) {
            connection.setAutoCommit(false);

            var id = model.getId();

            if(model.getGroupName()!=null){
                preparedStatement.setString(1, model.getGroupName());
                preparedStatement.setInt(2, model.getMonthlyList());
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
            }

            if(model.getArtistData()!=null){
                saveArtistData(model.getArtistData(), id);
            }

            if(model.getAlbum()!=null){

                for(var album:model.getAlbum()){
                    saveAlbum(album, id);
                }


            }

            connection.commit();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //INSERT AND UPDATE FOR ARTIST DATA

    private void insertArtistData(ArtistData model, int id) {
        try(
                var artistDataStatement = connection.prepareStatement(INSERT_ARTIST_DATA)
                ) {
            artistDataStatement.setInt(1, id);
            artistDataStatement.setString(2, model.getCountry());
            artistDataStatement.setString(3, model.getLang());
            artistDataStatement.setInt(4, model.getDebutYear());
            artistDataStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void updateArtistData(ArtistData model, int id) {
        try(
                var statement = connection.prepareStatement("UPDATE ARTIST_DATA SET COUNTRY = ?, LANG = ?, DEBUT_YEAR = ? WHERE ID_ARTIST = ?")
                ){
            statement.setString(1, model.getCountry());
            statement.setString(2, model.getLang());
            statement.setInt(3, model.getDebutYear());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //INSERT AND UPDATE FOR ALBUM

    private void insertAlbum(Album model, int id) {
        try(
                var statement = connection.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS)
                ) {
            statement.setInt(1, id);
            statement.setString(2, model.getAlbumName());
            statement.setInt(3, model.getNSongs());
            statement.executeUpdate();
            //testing
            var keysAlbum = statement.getGeneratedKeys();
            if(keysAlbum.next()){
                model.setId(keysAlbum.getInt(1));
            }

            if(model.getSong()!=null){
                for(var song:model.getSong()){

                    saveSong(song,id,model.getId());

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateAlbum(Album model, int id) {
        try(
                var statement = connection.prepareStatement("UPDATE ALBUM SET ALBUM_NAME = ?, QUANTITY_SONGS = ? WHERE ID_ARTIST = ? AND ID_ALBUM = ?");

                ) {
            int idAlbum = model.getId();
            statement.setString(1, model.getAlbumName());
            statement.setInt(2, model.getNSongs());
            statement.setInt(3, id);
            statement.setInt(4, idAlbum);
            statement.executeUpdate();

            if(model.getSong()!=null){
                for(var song:model.getSong()){
                    saveSong(song, id, idAlbum);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //INSERT AND UPDTE FOR SONG

    private void insertSong(Song model, int idArtist, int idAlbum) {
        try(
                var statement = connection.prepareStatement(INSERT_SONG, Statement.RETURN_GENERATED_KEYS)
                ) {
            statement.setInt(1, idAlbum);
            statement.setInt(2, idArtist);
            statement.setString(3, model.getSongName());
            statement.setInt(4, model.getDuration());
            statement.executeUpdate();
            var keySong = statement.getGeneratedKeys();
            if(keySong.next()){
                model.setId(keySong.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateSong(Song model, int id) {
        try(
                var statement = connection.prepareStatement("UPDATE SONG SET SONG_NAME = ?, DURATION = ? WHERE ID_ALBUM = ? AND ID_SONG = ?")
                ) {
            int idSong = model.getId();
            statement.setString(1, model.getSongName());
            statement.setInt(2, model.getDuration());
            statement.setInt(3, id);
            statement.setInt(4, idSong);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete(Artist model) {
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ARTIST where ID_ARTIST = ?");
            //PreparedStatement albumStatement = connection.prepareStatement("DELETE FROM ALBUM where ID_ARTIST = ?");
            //PreparedStatement songStatement = connection.prepareStatement("DELETE FROM SONG WHERE ID_ARTIST = ?")
        ) {

            connection.setAutoCommit(false);

            preparedStatement.setInt(1,model.getId());
            /*
            albumStatement.setInt(1,model.getId());
            songStatement.setInt(1,model.getId());

            songStatement.executeUpdate();
            */


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
        String query2 = "SELECT * FROM ALBUM WHERE ID_ARTIST = ?";
        String query3 = "SELECT * FROM SONG WHERE ID_ARTIST = ? AND ID_ALBUM = ?";
        String query4 = "SELECT * FROM ARTIST_DATA WHERE ID_ARTIST =?";

        try (PreparedStatement statement = connection.prepareStatement(query);
        PreparedStatement albumStatement = connection.prepareStatement(query2);
        PreparedStatement songStatement = connection.prepareStatement(query3);
        PreparedStatement artistDataStatment = connection.prepareStatement(query4)
        ) {
            statement.setInt(1, id);
            albumStatement.setInt(1, id);
            songStatement.setInt(1, id);
            artistDataStatment.setInt(1, id);

            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Artist result = new cat.uvic.teknos.musicrep.domain.jdbc.models.Artist();
                    result.setId(resultSet.getInt("ID_ARTIST"));
                    result.setGroupName(resultSet.getString("GROUP_NAME"));
                    result.setMonthlyList(resultSet.getInt("MONTHLY_LIST"));

                    var resultSetData = artistDataStatment.executeQuery();

                    getArtistData(resultSetData, result);

                    getAlbumAndSong(albumStatement, songStatement, result);

                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }


    //check getAll
    @Override
    public Set<Artist> getAll() {
        String query = "SELECT * FROM ARTIST";
        String query2 = "SELECT * FROM ALBUM WHERE ID_ARTIST = ?";
        String query3 = "SELECT * FROM SONG WHERE ID_ARTIST = ? AND ID_ALBUM = ?";
        String query4 = "SELECT * FROM ARTIST_DATA";

        try (PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement albumStatement = connection.prepareStatement(query2);
             PreparedStatement songStatement = connection.prepareStatement(query3);
             PreparedStatement artistDataStatment = connection.prepareStatement(query4)
        ) {

            int id = 1;
            try (var resultSet = statement.executeQuery()) {
                var artists = new HashSet<Artist>();
                var resultSetData = artistDataStatment.executeQuery();


                while (resultSet.next()) {

                    //There is some trouble when printing albums and song to their respective artists
                    //So program will handle these two this way for now
                    albumStatement.setInt(1, id);
                    songStatement.setInt(1, id);
                    id++;


                    Artist result = new cat.uvic.teknos.musicrep.domain.jdbc.models.Artist();
                    result.setId(resultSet.getInt("ID_ARTIST"));
                    result.setGroupName(resultSet.getString("GROUP_NAME"));
                    result.setMonthlyList(resultSet.getInt("MONTHLY_LIST"));



                    getArtistData(resultSetData, result);

                    getAlbumAndSong(albumStatement, songStatement, result);

                    artists.add(result);

                }
                return artists;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAlbumAndSong(PreparedStatement albumStatement, PreparedStatement songStatement, Artist result) throws SQLException {
        var albums = new HashSet<Album>();
        var resultSetAlbum = albumStatement.executeQuery();
        while(resultSetAlbum.next()){
            Album resultAlbum = new cat.uvic.teknos.musicrep.domain.jdbc.models.Album();
            resultAlbum.setId(resultSetAlbum.getInt("ID_ALBUM"));
            resultAlbum.setAlbumName(resultSetAlbum.getString("ALBUM_NAME"));
            resultAlbum.setNSongs(resultSetAlbum.getInt("QUANTITY_SONGS"));

            var songs = new HashSet<Song>();
            songStatement.setInt(2, resultAlbum.getId());
            var resultSetSong = songStatement.executeQuery();
            while(resultSetSong.next()){
                Song resultSong = new cat.uvic.teknos.musicrep.domain.jdbc.models.Song();
                resultSong.setId(resultSetSong.getInt("ID_SONG"));
                resultSong.setSongName(resultSetSong.getString("SONG_NAME"));
                resultSong.setDuration(resultSetSong.getInt("DURATION"));
                songs.add(resultSong);
                resultAlbum.setSong(songs);
            }

            albums.add(resultAlbum);
            result.setAlbum(albums);
        }
    }

    private static void getArtistData(ResultSet resultSetData, Artist result) throws SQLException {
        if(resultSetData.next()){
            ArtistData resultData = new cat.uvic.teknos.musicrep.domain.jdbc.models.ArtistData();
            resultData.setCountry(resultSetData.getString("COUNTRY"));
            resultData.setLang(resultSetData.getString("LANG"));
            resultData.setDebutYear(resultSetData.getInt("DEBUT_YEAR"));

            result.setArtistData(resultData);
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

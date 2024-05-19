package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import com.esori.list.repositories.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcRepositoryFactory implements RepositoryFactory {

    private Connection connection;
    public JdbcRepositoryFactory(){
        try {
            var properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/datasource.properties"));
            connection = DriverManager.getConnection(String.format("%s:%s://%s/%s",
                    properties.getProperty("protocol"),
                    properties.getProperty("subprotocol"),
                    properties.getProperty("url"),
                    properties.getProperty("database")), properties.getProperty("user"), properties.getProperty("password"));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserRepository getUserRepository() {
        return new JdbcUserRepository(connection);
    }

    @Override
    public ArtistRepository getArtistRepository() {
        return new JdbcArtistRepository(connection);
    }

    @Override
    public PlaylistRepository getPlaylistRepository() {
        return new JdbcPlaylistRepository(connection);
    }
}

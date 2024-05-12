package cat.uvic.teknos.musicrep.domain.jdbc.repositories;

import cat.uvic.teknos.musicrep.domain.jdbc.models.*;
import com.fcardara.dbtestutils.junit.CreateSchemaExtension;
import com.fcardara.dbtestutils.junit.DbAssertions;
import com.fcardara.dbtestutils.junit.GetConnectionExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

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

        Album album1 = new Album();
        album1.setAlbumName("Post Human");
        album1.setNSongs(8);

        Song song = new Song();
        song.setSongName("Can you feel my heart");
        song.setDuration(229);

        Song song2 = new Song();
        song2.setSongName("Empire");
        song2.setDuration(226);


        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);
        songs.add(song2);

        album.setSong(songs);

        var albums = new HashSet<com.esori.list.models.Album>();
        albums.add(album);
        albums.add(album1);

        ArtistData artistData = new ArtistData();
        artistData.setCountry("USA");
        artistData.setLang("ENG");
        artistData.setDebutYear(2006);

        artist.setAlbum(albums);
        artist.setArtistData(artistData);

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
    void shouldUpdateArtist(){

        //checks updating just Artist group name for this one
        Artist artist1 = new Artist();
        artist1.setId(3);
        artist1.setGroupName("Ghost");


        Artist artist = new Artist();
        artist.setId(1);
        artist.setGroupName("The Offspring");

        ArtistData artistData = new ArtistData();
        artistData.setId(artist.getId());
        artistData.setCountry("AAA");

        Album album = new Album();
        album.setId(1);
        album.setAlbumName("Americana");

        Song song = new Song();
        song.setId(1);
        song.setSongName("Pretty Fly");
        song.setDuration(189);

        //checks by adding a new song to the update
        Song song1 = new Song();
        song1.setSongName("Gone Away");
        song1.setDuration(268);


        var songs = new HashSet<com.esori.list.models.Song>();
        songs.add(song);
        songs.add(song1);

        album.setSong(songs);

        var albums = new HashSet<com.esori.list.models.Album>();
        albums.add(album);


        artist.setArtistData(artistData);
        artist.setAlbum(albums);


        var repository = new JdbcArtistRepository(connection);
        repository.save(artist);
        repository.save(artist1);

    }

    @Test
    void delete() {
        Artist artist = new Artist();
        artist.setId(2);

        var repository = new JdbcArtistRepository(connection);
        repository.delete(artist);

        DbAssertions.assertThat(connection)
                .table("ARTIST")
                .where("ID_ARTIST", artist.getId())
                .doesNotExist();
    }

    @Test
    void get() {
        int id = 1;
        var repository = new JdbcArtistRepository(connection);

        com.esori.list.models.Artist artist = repository.get(id);
        SoutArtist(artist);
    }

    @Test
    void getAll() {
        var repository = new JdbcArtistRepository(connection);
        Set<com.esori.list.models.Artist> artists = repository.getAll();
        assertNotNull(artists);

        for(var artist:artists){
            SoutArtist(artist);
        }
    }


    private static void SoutArtist(com.esori.list.models.Artist artist){
        System.out.println("Artist " +  artist.getId());
        System.out.println("Group name: " + artist.getGroupName());
        System.out.println("Total month listeners: " +  artist.getMonthlyList());
        if(artist.getArtistData()!=null){
            System.out.println("-----\nArtist Data");
            System.out.println("Origin: " +  artist.getArtistData().getCountry());
            System.out.println("Songs language: " + artist.getArtistData().getLang());
            System.out.println("Artist debut year: " + artist.getArtistData().getDebutYear());
        }
        if(artist.getAlbum()!=null){
            System.out.println("**\nAlbums");
            for(var album:artist.getAlbum()){
                System.out.println("-----\nAlbum name: " + album.getAlbumName());
                System.out.println("Total songs in album: " + album.getNSongs());

                if(album.getSong()!=null){
                    System.out.println("**\nSongs");
                    for(var song:album.getSong()){
                        System.out.println("-----\nTitle: " + song.getSongName());
                        System.out.println("Duration: " + song.getDuration());
                    }
                }
            }
        }
        System.out.println("\n\n");
    }

}
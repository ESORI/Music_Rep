package cat.uvic.teknos.musicrep.domain.jdbc.models;

import com.esori.list.models.Album;
import com.esori.list.models.ArtistData;

import java.util.Set;

public class Artist implements com.esori.list.models.Artist {

    private int id;
    private String groupName;
    private int monthlyList;
    private Set<Album> album;
    private ArtistData artistData;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getGroupName() {
        return groupName;
    }

    @Override
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMonthlyList() {
        return monthlyList;
    }

    @Override
    public void setMonthlyList(int monthlyList) {
        this.monthlyList=monthlyList;
    }

    @Override
    public Set<Album> getAlbum() {
        return album;
    }

    @Override
    public void setAlbum(Set<Album> album) {
        this.album=album;
    }

    @Override
    public ArtistData getArtistData() {
        return artistData;
    }

    @Override
    public void setArtistData(ArtistData artistData) {
        this.artistData = artistData;
    }
}

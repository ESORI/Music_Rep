package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Album;
import com.esori.list.models.ArtistData;
import com.esori.list.models.Song;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ARTIST")
public class Artist implements com.esori.list.models.Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ARTIST")
    private int id;
    @Column(name = "GROUP_NAME")
    private String groupName;
    @Column(name = "MONTHLY_LIST")
    private int monthlyList;


    @OneToMany(
            //mappedBy = "artist",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Album.class)
    @JoinColumn(name = "ID_ARTIST")
    private Set<Album> album = new HashSet<>();



    @OneToOne(
            mappedBy = "artist",
            cascade = CascadeType.ALL,
            targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.ArtistData.class)
    @PrimaryKeyJoinColumn
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
        this.artistData.setArtist(this);
    }


}

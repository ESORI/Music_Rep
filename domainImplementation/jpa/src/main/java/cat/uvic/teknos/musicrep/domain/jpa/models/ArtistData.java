package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.Artist;
import jakarta.persistence.*;

@Entity
@Table(name="ARTIST_DATA")
public class ArtistData implements com.esori.list.models.ArtistData {

    @Id
    @Column(name = "ID_ARTIST")
    private int id;
    @Column(name = "COUNTRY", columnDefinition="char(3)")
    private String country;
    @Column(name = "LANG", columnDefinition = "char(3)")
    private String lang;
    @Column(name = "DEBUT_YEAR")
    private int debutYear;

    @OneToOne(targetEntity = cat.uvic.teknos.musicrep.domain.jpa.models.Artist.class)
    @MapsId
    @JoinColumn(name="ID_ARTIST")
    private Artist artist;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public int getDebutYear() {
        return debutYear;
    }

    @Override
    public void setDebutYear(int debutYear) {
        this.debutYear=debutYear;
    }

    @Override
    public Artist getArtist() {
        return artist;
    }

    @Override
    public void setArtist(Artist artist) {
        this.artist = artist;
        this.id = artist.getId();
    }
}


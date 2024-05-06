package cat.uvic.teknos.musicrep.domain.jpa.models;

import jakarta.persistence.*;

@Entity
@Table(name="ARTIST DATA")
public class ArtistData implements com.esori.list.models.ArtistData {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "LANGUAGE")
    private String lang;
    @Column(name = "DEBUT YEAR")
    private int debutYear;

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
}

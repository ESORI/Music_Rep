package cat.uvic.teknos.models;

import com.esori.list.models.Artist;

import java.io.Serializable;

public class ArtistData implements com.esori.list.models.ArtistData, Serializable {

    private int id;
    private String country;
    private String lang;
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

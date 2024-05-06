package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.User;
import jakarta.persistence.*;

@Entity
@Table(name="USER DATA")
public class UserData implements com.esori.list.models.UserData {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    @Transient
    @Column(name = "USER")
    private User user;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PHONE NUMBER")
    private int phoneNumber;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "AGE")
    private int age;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }


    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String name) {
        this.userName=userName;
    }

    @Override
    public int getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country=country;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }
}

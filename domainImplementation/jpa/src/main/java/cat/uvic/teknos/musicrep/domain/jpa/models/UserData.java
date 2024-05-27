package cat.uvic.teknos.musicrep.domain.jpa.models;

import com.esori.list.models.User;
import jakarta.persistence.*;

@Entity
@Table(name="USER_DATA")
public class UserData implements com.esori.list.models.UserData {

    @Id
    @Column(name="ID_USER")
    private int id;

    @Column(name="USER_NAME")
    private String userName;
    @Column(name="PHONE_NUM")
    private int phoneNumber;
    @Column(name="COUNTRY")
    private String country;
    @Column(name="AGE")
    private int age;

    @OneToOne(targetEntity=cat.uvic.teknos.musicrep.domain.jpa.models.User.class, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="ID_USER")
    private User user;


    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id=id;
    }
    @OneToOne(targetEntity=cat.uvic.teknos.musicrep.domain.jpa.models.User.class)
    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user=user;
        this.id = user.getId();
    }


    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String name) {
        this.userName=name;
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

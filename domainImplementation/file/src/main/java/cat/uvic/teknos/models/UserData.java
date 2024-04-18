package cat.uvic.teknos.models;

import com.esori.list.models.User;

import java.io.Serializable;

public class UserData implements com.esori.list.models.UserData, Serializable {

    private int id;
    private User user;
    private String userName;
    private int phoneNumber;
    private String country;
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
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user=user;
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

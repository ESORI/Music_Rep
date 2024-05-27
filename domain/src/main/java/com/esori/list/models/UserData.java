package com.esori.list.models;

public interface UserData {

    int getId();
    void setId(int id);

    User getUser();
    void setUser(User user);

    String getUserName();
    void setUserName(String name);

    int getPhoneNumber();
    void setPhoneNumber(int phoneNumber);

    String getCountry();
    void setCountry(String country);

    int getAge();
    void setAge(int age);
}

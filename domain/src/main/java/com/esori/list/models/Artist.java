package com.esori.list.models;

import java.util.Set;

public interface Artist {
    int getId();
    void setId(int id);

    String getGroupName();
    void setGroupName(String groupName);

    int getMonthlyList();
    void setMonthlyList(int monthlyList);

    Set<Album> getAlbum();
    void setAlbum(Set<Album> album);

}

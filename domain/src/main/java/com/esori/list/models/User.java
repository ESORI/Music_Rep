package com.esori.list.models;

import java.util.Set;

public interface User {
    int getId();
    void setId(int id);

    String getUsername();
    void setUsername(String username);

    Set<Playlist> getPlaylist();
    void setPlaylist(Set<Playlist> playlist);
}

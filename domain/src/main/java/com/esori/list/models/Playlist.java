package com.esori.list.models;

import java.util.Set;

public interface Playlist {
    int getId();
    void setId(int id);

    int getNSongs();
    void setNSongs(int nSongs);

    String getPlaylistName();
    void setPlaylistName(String playlistName);

    String getDescription();
    void setDescription(String description);

    int getDuration();
    void setDuration(int duration);

    Set<Song> getSong();
    void setSong(Set<Song> song);

    Set<User> getUser();
    void setUser(Set<User> user);
}

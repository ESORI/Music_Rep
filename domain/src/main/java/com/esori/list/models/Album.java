package com.esori.list.models;

import java.util.Set;

public interface Album {
    int getId();
    void setId(int id);

    String getAlbumName();
    void setAlbumName(String albumName);

    int getNSongs();
    void setNSongs(int nSongs);

    Artist getArtist();
    void setArtist(Artist artist);

    Set<Song> getSong();
    void setSong(Set<Song> song);

}

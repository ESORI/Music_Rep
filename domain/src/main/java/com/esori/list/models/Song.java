package com.esori.list.models;

import java.util.Set;

public interface Song {
    int getId();
    void setId(int id);

    String getSongName();
    void setSongName(String songName);

    int getDuration();
    void setDuration(int duration);

    Artist getArtist();
    void setArtist(Artist artist);

    Album getAlbum();
    void setAlbum(Album album);

    Set<Playlist> getPlaylist();
    void setPlaylist(Set<Playlist> playlist);
}

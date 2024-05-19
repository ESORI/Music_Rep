package com.esori.list.models;

public interface ModelFactory {
    User createUser();
    Artist createArtist();
    Playlist createPlaylist();
}

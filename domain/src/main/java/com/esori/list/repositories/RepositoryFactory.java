package com.esori.list.repositories;

public interface RepositoryFactory {
    UserRepository getUserRepository();
    ArtistRepository getArtistRepository();
    PlaylistRepository getPlaylistRepository();
}

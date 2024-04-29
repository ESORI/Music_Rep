package com.esori.list.repositories;

public interface RepositoryFactory {
    UserRepository getUserRepository();
    SongRepository getSongRepository();
    ArtistRepository getArtistRepository();
    PlaylistRepository getPlaylistRepository();
}

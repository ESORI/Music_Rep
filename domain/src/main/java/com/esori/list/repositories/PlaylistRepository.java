package com.esori.list.repositories;

import com.esori.list.models.Artist;
import com.esori.list.models.Playlist;

import java.util.Set;

public interface PlaylistRepository extends Repository<Integer, Playlist>{
    @Override
    void save(Playlist model);

    void update(Playlist model);
    @Override
    void delete(Playlist model);

    @Override
    Playlist get(Integer id);

    @Override
    Set<Playlist> getAll();
}

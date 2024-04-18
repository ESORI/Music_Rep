package com.esori.list.repositories;

import com.esori.list.models.Playlist;
import com.esori.list.models.Song;

import java.util.Set;

public interface SongRepository extends Repository<Integer, Song>{
    @Override
    void save(Song model);


    @Override
    void delete(Song model);

    @Override
    Song get(Integer id);

    @Override
    Set<Song> getAll();
}

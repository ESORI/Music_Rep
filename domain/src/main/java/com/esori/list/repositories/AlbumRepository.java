package com.esori.list.repositories;

import com.esori.list.models.Album;

import java.util.Set;

public interface AlbumRepository extends Repository<Integer, Album> {
    @Override
    void save(Album model);

    void update(Album model);

    @Override
    void delete(Album model);

    @Override
    Album get(Integer id);

    @Override
    Set<Album> getAll();
}

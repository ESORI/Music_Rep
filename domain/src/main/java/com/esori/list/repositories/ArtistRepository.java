package com.esori.list.repositories;

import com.esori.list.models.Artist;
import com.esori.list.models.ArtistData;

import java.util.Set;

public interface ArtistRepository extends Repository<Integer, Artist>{
    @Override
    void save(Artist model);

    void update(Artist model);
    @Override
    void delete(Artist model);

    @Override
    Artist get(Integer id);

    @Override
    Set<Artist> getAll();
}

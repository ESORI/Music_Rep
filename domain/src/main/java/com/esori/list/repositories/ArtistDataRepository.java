package com.esori.list.repositories;

import com.esori.list.models.Album;
import com.esori.list.models.ArtistData;

import java.util.Set;

public interface ArtistDataRepository extends Repository<Integer, ArtistData>{

    @Override
    void save(ArtistData model);

    @Override
    void delete(ArtistData model);

    @Override
    ArtistData get(Integer id);

    @Override
    Set<ArtistData> getAll();
}

package com.esori.list.repositories;

import com.esori.list.models.Song;
import com.esori.list.models.UserData;

import java.util.Set;

public interface UserDataRepository extends Repository<Integer, UserData>{
    @Override
    void save(UserData model);

    void update(UserData model);
    @Override
    void delete(UserData model);

    @Override
    UserData get(Integer id);

    @Override
    Set<UserData> getAll();
}

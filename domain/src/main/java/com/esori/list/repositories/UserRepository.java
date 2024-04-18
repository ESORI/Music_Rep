package com.esori.list.repositories;

import com.esori.list.models.User;
import com.esori.list.models.UserData;

import java.util.Set;

public interface UserRepository extends Repository<Integer, User>{
    @Override
    void save(User model);

    @Override
    void delete(User model);

    @Override
    User get(Integer id);

    @Override
    Set<User> getAll();

    User getByUsername (String username);

}

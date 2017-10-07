package com.besteam.bestapp.dao;

import com.besteam.bestapp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    public User findUserByUsername(String name);
}

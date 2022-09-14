package org.example.dao;

import org.example.model.User;
import org.example.service.UserService;

import java.util.Optional;

public interface UserDao extends BasicDao<User> {
    Optional<User> findByUsernameAndPassword(String username,String password);
    User findByUserId(Integer id);
}

package org.example.service;

import org.example.model.Message;

public interface UserService {
    Message checkUser(String username,String password);
}

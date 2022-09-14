package org.example.service.imp;

import org.example.dao.UserDao;
import org.example.dao.imp.UserDaoImpl;
import org.example.model.Message;
import org.example.model.User;
import org.example.service.UserService;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static UserService userService;
    private final UserDao userDao = UserDaoImpl.getUserDao();

    public static UserService getUserService(){
        if (userService==null){
            userService = new UserServiceImpl();
        }
        return userService;
    }

    @Override
    public Message checkUser(String username, String password) {
        Message message ;
        Optional<User> userOptional = userDao.findByUsernameAndPassword(username, password);
        if (userOptional.isPresent()){
            String key = Base64.getEncoder().encodeToString((username + "&" + password + "&" + new Date().getTime()).getBytes());
            message = new Message(0,"ok",key);
            return message;
        }else {
            message = new Message(1,"not found",null);
            return message;
        }
    }
}

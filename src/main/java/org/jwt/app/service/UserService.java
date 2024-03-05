package org.jwt.app.service;


import org.jwt.app.models.entities.User;

public interface UserService {



  User register(User user);



  boolean isUserExist(String email);
}

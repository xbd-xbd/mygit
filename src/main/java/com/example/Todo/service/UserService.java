package com.example.Todo.service;

import com.example.Todo.entity.User;

public interface UserService {
    User register(User user);

    User login(String username, String password);

    boolean isUsernameDuplicate(String username);
}

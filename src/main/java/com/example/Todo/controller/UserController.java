package com.example.Todo.controller;

import com.example.Todo.entity.User;
import com.example.Todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        User result = userService.register(user);
        if (result != null) {
            return "회원가입 성공!";
        } else {
            return "이미 존재하는 사용자입니다.";
        }
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User result = userService.login(user.getUsername(), user.getPassword());
        if (result != null) {
            return "로그인 성공!";
        } else {
            return "아이디 또는 비밀번호가 틀렸습니다.";
        }
    }
}
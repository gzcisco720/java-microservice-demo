package com.demo.thrift.user.edge.controller;

import com.demo.thrift.user.edge.dto.UserLoginDto;
import com.demo.thrift.user.edge.service.UserService;
import com.demo.thrift.user.service.api.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/auth")
    public UserDto authenticate(@RequestHeader("token") String token) {
        return userService.authenticate(token);
    }
}

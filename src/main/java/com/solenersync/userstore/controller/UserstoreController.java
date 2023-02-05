package com.solenersync.userstore.controller;

import com.solenersync.userstore.model.AuthResult;
import com.solenersync.userstore.model.User;
import com.solenersync.userstore.model.UserRequest;
import com.solenersync.userstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserstoreController {

    private final UserService userService;

    public UserstoreController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public Optional<User> getUser(@PathVariable Integer id) {
        log.debug("Retrieving user {} ",id);
        System.out.println("Retrieving user " + id);
        return userService.findById(id);
    }

    @GetMapping("/all")
    List<User> allUsers() {
        log.debug("Retrieving all users");
        return userService.findAll();
    }

    @PostMapping("/user/{id}/authenticate")
    public String authenticateUser(@PathVariable Integer id, @RequestBody UserRequest request) {
        AuthResult authResult = userService.authenticate(id, request.getPassword());
        log.debug("Authenticating user id {} result {} ",id, authResult);
        return "Hello there from userstore - this is your id 10001";
    }
}

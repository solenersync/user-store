package com.solenersync.userstore.controller;

import com.solenersync.userstore.model.User;
import com.solenersync.userstore.model.UserRequest;
import com.solenersync.userstore.model.UserUpdateRequest;
import com.solenersync.userstore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserstoreController {

    private final UserService userService;

    public UserstoreController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        log.info("Retrieving user {} ",id);
        return userService.findById(id).map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping("/user/update")
    public ResponseEntity<User> update(@RequestBody UserUpdateRequest request) {
        log.info("Updating user {} ",request.getEmail());
        return userService.update(request).map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @CrossOrigin
    @PostMapping("/user")
    public Optional<User> getUserByEmail(@RequestBody UserRequest request) {
        log.info("Retrieving user {} ",request.getEmail());
        return userService.findByEmail(request.getEmail());
    }

    @CrossOrigin
    @PostMapping("/user/create")
    public ResponseEntity<User> createUser(@RequestBody UserRequest request) {
        log.info("Creating user {}",request.getEmail());
        return userService.create(request).map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @CrossOrigin
    @PostMapping("/user/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestBody UserRequest request) {
        log.info("Authenticating user {} ",request.getEmail());
        return userService.authenticate(request.getEmail(), request.getPassword()).map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.internalServerError().build());
    }
}

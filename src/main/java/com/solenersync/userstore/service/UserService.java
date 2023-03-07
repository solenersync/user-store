package com.solenersync.userstore.service;

import com.solenersync.userstore.model.User;
import com.solenersync.userstore.model.UserRequest;
import com.solenersync.userstore.model.UserUpdateRequest;
import com.solenersync.userstore.respository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(Integer id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        List<User> userList = findAll();
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> update(UserUpdateRequest request) {
        Optional<User> optionalUser = this.findByEmail(request.getEmail());
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(request.getName());
            System.out.println(request.getName());
            System.out.println(user);
            User updatedUser = repository.save(user);
            return Optional.ofNullable(updatedUser);
        } else {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> create(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setRegisteredDate(LocalDateTime.now());
        return Optional.ofNullable(repository.save(user));
    }

    public Optional<User> authenticate(String email, String password) {
        return Optional.ofNullable(findByEmail(email).map(user -> passwordMatch(user, password)).orElse(null));
    }

    private User passwordMatch(User user, String password) {
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}

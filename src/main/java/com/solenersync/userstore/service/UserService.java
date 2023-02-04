package com.solenersync.userstore.service;

import com.solenersync.userstore.model.AuthResult;
import com.solenersync.userstore.model.User;
import com.solenersync.userstore.model.UserRequest;
import com.solenersync.userstore.respository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
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
    public Optional<User>findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(request.getPassword());
        user.setRegistered_date(new Date());
        User newUser = (User) repository.save(user);
        return newUser;
    }

    public AuthResult authenticate(Integer userId, String password) {
        return findById(userId).map(user -> passwordMatch(user, password)).orElse(AuthResult.INVALID);
    }

    private AuthResult passwordMatch(User user, String password) {
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (Objects.equals(user.getPassword(), passwordMd5)) {
            return AuthResult.VALID;
        }
        return AuthResult.INVALID;
    }
}

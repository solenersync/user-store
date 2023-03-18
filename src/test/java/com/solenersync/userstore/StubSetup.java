package com.solenersync.userstore;

import com.solenersync.userstore.model.User;
import com.solenersync.userstore.respository.UserRepository;
import com.solenersync.userstore.service.UserService;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@UtilityClass
public class StubSetup {

    User user = User.builder()
        .userId(1)
        .name("John Doe")
        .email("jd@test.com")
        .registeredDate(LocalDateTime.now())
        .build();


    public void stubForGetUserByEmail(UserRepository userRepository) {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
    }

    public void stubForCreateUser(UserRepository userRepository) {
        when(userRepository.save(any()))
            .thenAnswer(invocation -> user);
    }

    public void stubForUpdateUser(UserRepository userRepository) {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(userRepository.save(any()))
            .thenAnswer(invocation -> user);
    }
}
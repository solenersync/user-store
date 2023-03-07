package com.solenersync.userstore;

import com.solenersync.userstore.model.User;
import com.solenersync.userstore.model.UserRequest;
import com.solenersync.userstore.model.UserUpdateRequest;
import com.solenersync.userstore.respository.UserRepository;
import com.solenersync.userstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
class UserstoreServiceTests {

	private UserService userService;
	private UserRequest userRequest;
	private UserUpdateRequest userUpdateRequest;
	private User user;
	private LocalDateTime date;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	public void setUp() {
		userService = new UserService((userRepository));
		date = LocalDateTime.of(2022, 8, 12, 19, 30, 30);
		user = User.builder()
			.userId(10001)
			.name("test")
			.password("password")
			.email("test@test.com")
			.registeredDate(date)
			.build();

		userRequest = UserRequest.builder()
			.email("test@test.com")
			.name("test")
			.password("password")
			.build();

		userUpdateRequest = UserUpdateRequest.builder()
			.email("test@test.com")
			.name("test")
			.build();
	}

	@Test
	void should_return_user_when_found_by_id() {
		Integer id = 10001;
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Optional<User> result = userService.findById(id);
		assertThat(result).isEqualTo(Optional.of(user));
	}

	@Test
	void should_return_empty_when_user_not_found_by_id() throws Exception {
		Integer id = 10002;
		when(userRepository.findById(id)).thenReturn(Optional.empty());
		Optional<User> result = userService.findById(id);
		assertThat(result).isEmpty();
	}

	@Test
	void should_return_user_when_new_user_created() throws Exception {
		when(userRepository.save(any(User.class))).thenReturn(user);
		Optional<User> result = userService.create(userRequest);
		assertThat(result).isEqualTo(Optional.of(user));
	}

	@Test
	void should_return_empty_when_error_creating_user() throws Exception {
		when(userRepository.save(any(User.class))).thenReturn(null);
		Optional<User> result = userService.create(userRequest);
		assertThat(result).isEmpty();
	}

	@Test
	void should_return_new_user_when_user_is_updated() throws Exception {
		when(userRepository.findById(10001)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);
		Optional<User> result = userService.update(userUpdateRequest);
		assertThat(result).isEqualTo(Optional.of(user));
	}

	@Test
	void should_return_empty_when_error_updating_user() throws Exception {
		when(userRepository.save(any(User.class))).thenReturn(null);
		Optional<User> result = userService.create(userRequest);
		assertThat(result).isEmpty();
	}

	@Test
	void should_return_user_when_found_by_userid() throws Exception {
		Integer id = 10001;
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Optional<User> result = userService.findById(id);
		assertThat(result).isEqualTo(Optional.of(user));
	}

	@Test
	void should_return_empty_when_user_not_found_by_userid() throws Exception {
		Integer id = 10001;
		when(userRepository.findById(id)).thenReturn(Optional.empty());
		Optional<User> result = userService.findById(id);
		assertThat(result).isEmpty();
	}

	@Test
	void should_return_user_when_password_and_email_match() throws Exception {
		when(userRepository.findAll()).thenReturn(List.of(user));
		Optional<User> result = userService.authenticate("test@test.com", "password");
		assertThat(result).isEqualTo(Optional.of(user));
	}

	@Test
	void should_return_empty_when_password_and_email_do_not_match() throws Exception {
		when(userRepository.findAll()).thenReturn(List.of(user));
		Optional<User> result = userService.authenticate("test@test.com", "password1");
		assertThat(result).isEmpty();
	}

	@Test
	void should_return_empty_when_email_does_not_exist() throws Exception {
		when(userRepository.findAll()).thenReturn(List.of());
		Optional<User> result = userService.authenticate("test@test.com", "password");
		assertThat(result).isEmpty();
	}
}



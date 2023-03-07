package com.solenersync.userstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solenersync.userstore.controller.UserstoreController;
import com.solenersync.userstore.model.User;
import com.solenersync.userstore.model.UserRequest;
import com.solenersync.userstore.model.UserUpdateRequest;
import com.solenersync.userstore.service.UserService;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import javax.json.JsonObject;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({MockitoExtension.class})
class UserstoreControllerTests {

	private MockMvc mockMvc;
	private LocalDateTime date;
	private User user;
	private UserRequest userRequest;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Mock
	private UserService userService;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new UserstoreController(userService)).build();
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

	}

	@ParameterizedTest
	@JsonFileSource(resources = "/get-user.json")
	void should_return_user_when_found_by_id(JsonObject json) throws Exception {
		when(userService.findById(10001)).thenReturn(Optional.of(user));
		mockMvc.perform(get("/api/v1/users/user/10001"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(json.toString()));
	}

	@Test
	void should_return_404_when_user_not_found_by_id() throws Exception {
		when(userService.findById(10002)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/v1/users/user/10002"))
			.andExpect(status().isNotFound());
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/create-user.json")
	void should_return_ok_when_user_is_created(JsonObject json) throws Exception {
		when(userService.create(any(UserRequest.class))).thenReturn(Optional.of(new User()));
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/user/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/create-user.json")
	void should_return_internal_server_error_when_error_creating_user(JsonObject json) throws Exception {
		doAnswer(invocation -> {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating user");
		}).when(userService).create(any(UserRequest.class));
		mockMvc.perform(post("/api/v1/users/user/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andExpect(status().isInternalServerError());
		verify(userService, times(1)).create(any(UserRequest.class));
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/update-user.json")
	void should_return_ok_when_user_is_updated(JsonObject json) throws Exception {
		when(userService.update(any(UserUpdateRequest.class))).thenReturn(Optional.of(new User()));
		mockMvc.perform(post("/api/v1/users/user/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andExpect(status().isOk());
		verify(userService).update(any(UserUpdateRequest.class));
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/update-user.json")
	void should_return_internal_server_error_when_error_updating_user(JsonObject json) throws Exception {
		doAnswer(invocation -> {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating solar array");
		}).when(userService).update(any(UserUpdateRequest.class));
		mockMvc.perform(post("/api/v1/users/user/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString()))
			.andExpect(status().isInternalServerError());
		verify(userService, times(1)).update(any(UserUpdateRequest.class));
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/get-user.json")
	void should_return_user_when_found_by_userid(JsonObject json) throws Exception {
		when(userService.findById(10001)).thenReturn(Optional.of(user));
		mockMvc.perform(get("/api/v1/users/user/10001"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(json.toString()));
	}

	@Test
	void should_return_not_found_when_user_not_found_by_userid() throws Exception {
		when(userService.findById(10002)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/v1/users/user/10002"))
			.andExpect(status().isNotFound());
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/create-user.json")
	void should_return_user_when_authenticated(JsonObject json) throws Exception {
		when(userService.authenticate("test@test.com", "password")).thenReturn(Optional.of(user));
		mockMvc.perform(post("/api/v1/users/user/authenticate")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(userRequest)))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/create-user.json")
	void should_return_internal_server_error_when_not_authenticated(JsonObject json) throws Exception {
		when(userService.authenticate("test@test.com", "password")).thenReturn(Optional.empty());
		mockMvc.perform(post("/api/v1/users/user/authenticate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userRequest)))
			.andExpect(status().isInternalServerError());
		verify(userService, times(1)).authenticate(any(String.class), any(String.class));
	}
}



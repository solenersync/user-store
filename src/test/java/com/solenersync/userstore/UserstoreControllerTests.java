package com.solenersync.userstore;

import com.solenersync.userstore.controller.UserstoreController;
import com.solenersync.userstore.model.User;
import com.solenersync.userstore.service.UserService;
import net.joshka.junit.json.params.JsonFileSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import javax.json.JsonObject;
import java.time.LocalDateTime;
import java.util.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@ExtendWith({MockitoExtension.class})
class UserstoreControllerTests {

	private MockMvc mockMvc;

	@Mock
	private UserService service;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(new UserstoreController(service)).build();
	}

	@ParameterizedTest
	@JsonFileSource(resources = "/get-user.json")
	public void shouldReturnUserFromId(JsonObject json) throws Exception {

		LocalDateTime date = LocalDateTime.of(2022, 8, 12, 19, 30, 30);
		User user = User.builder()
			.user_id(10002)
			.name("Brian")
			.password("password")
			.email("test@test.com")
			.registered_date(date)
			.build();
		when(service.findById(10002)).thenReturn(Optional.of(user));
		mockMvc.perform(get("/api/v1/users/user/10002"))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(json.toString()));
	}

//	@Test
//	void shouldReturn404ForUnknownUser() throws Exception {
//		when(service.findById(000001)).thenReturn(Optional.empty());
//		mockMvc.perform(get("/api/v1/users/user/000001")).andExpect(status().isNotFound());
//	}
}



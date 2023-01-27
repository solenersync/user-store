package com.solenersync.userstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserstoreControllerTests {

	@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
	@Autowired
	private MockMvc mvc;

	@Test
	public void getHello() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/v1/users/user/10002").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Userstore response userid=10002")));
	}

	@Test
	public void returnUser() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/users/user").content("test@test.com").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("Hello there from userstore - this is your id 10001")));
	}

	@Test
	public void return400() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("/v1/users/user").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is4xxClientError());
	}

}

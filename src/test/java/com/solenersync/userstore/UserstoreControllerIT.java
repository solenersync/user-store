package com.solenersync.userstore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserstoreControllerIT {

	@Autowired
	private TestRestTemplate template;

	@Test
	public void getHello() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/", String.class);
		assertThat(response.getBody()).isEqualTo("Userstore response");
	}

	@Test
	public void returnUser() throws Exception {
		ResponseEntity<String> response = template.postForEntity("/user", "brian", String.class);
		assertThat(response.getBody()).contains("Hello there from userstore...brian");
	}

}

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

//	@Test
//	public void getHello() throws Exception {
//		ResponseEntity<String> response = template.getForEntity("/api/v1/users/user/10002", String.class);
//		assertThat(response.getBody()).isEqualTo("Userstore response userid=10002");
//	}
//
//	@Test
//	public void returnUser() throws Exception {
//		ResponseEntity<String> response = template.postForEntity("/api/v1/users/user", "test@test.com", String.class);
//		assertThat(response.getBody()).contains("Hello there from userstore - this is your id 10001");
//	}

}

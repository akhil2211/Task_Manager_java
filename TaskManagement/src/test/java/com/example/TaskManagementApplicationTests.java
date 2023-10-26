package com.example;

import com.example.Model.RegisterRequest;
import com.example.Repository.UserRepository;
import com.example.Service.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskManagementApplicationTests {

	@Mock
	private UserRepository userRepository;

	@Autowired
	private AdminService registrationService;
	@Test

	public void testDuplicateUserExists() {

		RegisterRequest registerRequest = new RegisterRequest("Akhil", "Nair", "akhil", "Akhil@123", "akhil@gmail.com", "1", "1");

		ResponseEntity<Object> response = registrationService.register(registerRequest);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}


}
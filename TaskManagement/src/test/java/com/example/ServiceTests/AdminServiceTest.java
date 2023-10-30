package com.example.ServiceTests;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import com.example.Model.Organization;
import com.example.Model.RegisterRequest;
import com.example.Repository.*;
import com.example.Service.AdminService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

	@Mock

	private UserRepository userRepository;

	@Mock
	private OrganizationRepo organizationRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private RoleRepo roleRepo;

	@Mock
	private PriorityRepo priorityRepo;
	@Mock
	private CategoryRepo categoryRepo;

	private AdminService adminService;

	private RegisterRequest registerRequest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.adminService = new AdminService(this.organizationRepo, this.passwordEncoder, this.roleRepo, this.userRepository, this.priorityRepo, this.categoryRepo);

		registerRequest = new RegisterRequest("Mahesh", "K", "mahesh", "Mahesh@123", "mahesh@gmail.com", "1", "1");
	}
	@Test
	public void RegisterTest(){
		RegisterRequest registerRequest = new RegisterRequest("Mahesh", "K", "mahesh", "Mahesh@123", "mahesh@gmail.com", "1", "1");
		when(userRepository.existsByEmail("mahesh@gmail.com")).thenReturn(false);
		when(userRepository.existsByUsername("mahesh")).thenReturn(false);
		ResponseEntity<Object> response = adminService.register(registerRequest);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testDuplicateUserExists() {
		when(userRepository.existsByEmail("mahesh@gmail.com")).thenReturn(true);
		when(userRepository.existsByUsername("mahesh")).thenReturn(true);
		ResponseEntity<Object> response = adminService.register(registerRequest);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	@Test
	public void testCreateOrganization() {
		Map<String, String> orgRequest = new HashMap<>();
		orgRequest.put("org_name", "ThinkPalm");
		orgRequest.put("org_code", "TP01");

		ResponseEntity<Object> result = adminService.createOrganization(orgRequest);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);

	}
}

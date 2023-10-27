package com.example.ServiceTests;

import com.example.Model.Organization;
import com.example.Model.RegisterRequest;
import com.example.Model.Role;
import com.example.Model.User;
import com.example.Repository.*;
import com.example.Service.AdminService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;


import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TaskManagementApplicationTests {
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
	@Mock
	private AdminService adminService;
	private RegisterRequest registerRequest;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		this.adminService=new AdminService(this.organizationRepo,this.passwordEncoder,this.roleRepo,this.userRepository,this.priorityRepo,this.categoryRepo);
		registerRequest = new RegisterRequest("Mahesh","K","mahesh","Mahesh@123","mahesh@gmail.com","1","1");
		adminService.register(registerRequest);
//		Role role = roleRepo.findById(1).orElse(null);
//		Organization organization=organizationRepo.findById(1).orElse(null);
//		User user=User.builder()
//				.firstname("Anna")
//				.lastname("Nair")
//				.username("annan")
//				.password("Annan@123")
//				.email("anna@gmail.com")
//				.role(role)
//				.organization(organization)
//				.build();
//
//		userRepository.save(user);
	}

	@Test

	public void testDuplicateUserExists() {

		registerRequest = new RegisterRequest("Mahesh","K","mahesh","Mahesh@123","mahesh@gmail.com","1","1");

		ResponseEntity<Object> response = adminService.register(registerRequest);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

}
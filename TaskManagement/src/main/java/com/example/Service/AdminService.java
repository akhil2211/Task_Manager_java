package com.example.Service;

import com.example.Authorization.JwtService;
import com.example.Model.*;
import com.example.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

@Service

public class AdminService {

    private final OrganizationRepo organizationRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final UserRepository userRepository;

    private final PriorityRepo priorityRepo;

       @Autowired
      public AdminService(OrganizationRepo organizationRepo, PasswordEncoder passwordEncoder, RoleRepo roleRepo, UserRepository userRepository, TokenRepo tokenRepo, JwtService jwtService, PriorityRepo priorityRepo) {
        this.organizationRepo = organizationRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.userRepository = userRepository;
        this.priorityRepo = priorityRepo;
       }


      public User register(RegisterRequest registerRequest) {
        User user= new User();
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        Role role= roleRepo.findById(registerRequest.getRoleId()).orElse(null);
        user.setRole(role);
        Organization organization=organizationRepo.findById(registerRequest.getOrgId()).orElse(null);
        user.setOrganization(organization);

        var savedUser= userRepository.save(user);
        return savedUser;
    }

    public String createOrganziation(Map<String,String> orgRequest) {
        Organization organization=new Organization();
        organization.setOrg_code(orgRequest.get("name"));
        organization.setOrg_name(orgRequest.get("code"));
        organizationRepo.save(organization);
        return "Organization Added";
    }
    public String createPriority(Map<String,String> priorityRequest) {
        Priority priority =new Priority();
        priority.setType(priorityRequest.get("priority"));
        priorityRepo.save(priority);
        return "Priority Created";
       }

}

package com.example.Controller;

import com.example.Authorization.AuthService;
import com.example.Model.RegisterRequest;
import com.example.Model.User;
import com.example.Service.AdminService;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {
    private final AdminService adminService;

    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public AdminController(AdminService adminService, AuthService authService, UserService userService) {
        this.adminService = adminService;
        this.authService = authService;
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> register (@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(adminService.register(registerRequest));
    }
    @PostMapping("/createOrganization")
    public ResponseEntity<String> createOrganziation(@RequestBody Map<String,String> orgRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createOrganziation(orgRequest));
    }


}

package com.example.Controller;

import com.example.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")

public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/createOrganization")
    public ResponseEntity<String> createOrganziation(@RequestBody Map<String, String> orgRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createOrganziation(orgRequest));

    }
}

package com.example.Controller;

import com.example.Model.Project;
import com.example.Model.Task;
import com.example.Model.User;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")

public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{user_id}")
    public ResponseEntity<User> getUserDetails(@PathVariable Integer user_id){
        return ResponseEntity.ok(userService.getUserDetails(user_id));
    }
    @GetMapping("/currentUserProfile")
    public ResponseEntity<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = (User) authentication.getPrincipal();
        Integer currentUserId = userDetails.getId();
        userDetails.setPassword("");
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer user_id) {
        User user = userService.getUserById(user_id);
        if (user != null)
        {
            return ResponseEntity.ok(user);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/list")
    public ResponseEntity <List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/organization/{organization_id}")
    public ResponseEntity<List<User>> findByOrganization(@PathVariable Integer organization_id) {
        return new ResponseEntity<>(userService.getUserByOrganization(organization_id), HttpStatus.OK);
    }


}

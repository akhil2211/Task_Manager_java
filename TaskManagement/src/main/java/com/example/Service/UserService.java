package com.example.Service;


import com.example.Model.Task;
import com.example.Model.User;
import com.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    public List<User> getAllUsers(){
       return userRepo.findAllUsers();
    }

    public User getUserById(Integer user_id) {

        return userRepo.getUserById(user_id);
    }

    public List<User> getUserByOrganization(Integer org_id) {
        return userRepo.findByOrganization(org_id);
    }

    public User getUserDetails(Integer userId) {
        User user= userRepo.getUserById(userId);
        user.setPassword("");
        return user;

    }
}

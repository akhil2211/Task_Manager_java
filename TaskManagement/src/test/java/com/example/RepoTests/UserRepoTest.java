package com.example.RepoTests;

import com.example.Model.RegisterRequest;
import com.example.Model.User;
import com.example.Repository.UserRepository;
import com.example.Service.AdminService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserRepoTest {
    @Autowired
    private UserRepository userRepository;


    @Test
    public void setRegTest(){
        User user= User.builder()
                .firstname("Manu")
                .lastname("M")
                .username("manu")
                .password("Manu@123")
                .email("manu@gmail.com")
                .role(null)
                .organization(null)
                .build();

            userRepository.save(user);
           Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    public void getUserTest(){
        User user= userRepository.findById(1).get();
        Assertions.assertThat(user.getId()).isEqualTo(1);
    }
    @Test
    public void getUserListTest(){
        List<User> users=userRepository.findAllUsers();
        Assertions.assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    public void getByUserNameTest(){
        User user= userRepository.findByUsername("manu").get();
        Assertions.assertThat(user.getUsername()).isEqualTo("manu");
    }
    @Test
    public void saveDuplicateUserTest(){

        User user= User.builder()
                .firstname("Mayu")
                .lastname("M")
                .username("mayu")
                .password("Mayu@123")
                .email("manu@gmai.com")
                .role(null)
                .organization(null)
                .build();
        assertThrows(DataIntegrityViolationException.class,() -> userRepository.save(user));
    }

    @Test
    public void saveUniqueUserTest(){

        User user= User.builder()
                .firstname("Karun")
                .lastname("M")
                .username("karun")
                .password("Karun@123")
                .email("karun@gmai.com")
                .role(null)
                .organization(null)
                .build();
        userRepository.save(user);
        Assertions.assertThat(user.getId()).isGreaterThan(0);
    }

    }



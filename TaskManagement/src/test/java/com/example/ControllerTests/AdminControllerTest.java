package com.example.ControllerTests;

import com.example.Controller.AdminController;
import com.example.Model.RegisterRequest;
import com.example.Model.User;
import com.example.Service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdminService adminService;


    @Test
    public void testRegister() throws Exception {
        Map<String, String> registerRequest = new HashMap<>();
        RegisterRequest request = new RegisterRequest("Mahesh", "K", "mahesh", "Mahesh@123", "mahesh@gmail.com", "1", "1");

        Mockito.when(adminService.register(any())).thenReturn(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/admin/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(MockMvcResultMatchers.content().string((Matcher<? super String>) request));
    }


}

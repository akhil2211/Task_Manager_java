package com.example.Controller;


import com.example.AuthFilter.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor


public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
   }
    @PostMapping("/login")
    public ResponseEntity<String> register (@RequestBody AuthenticationRequest registerRequest){
        return ResponseEntity.ok(authService.authenticate(registerRequest));
    }
}


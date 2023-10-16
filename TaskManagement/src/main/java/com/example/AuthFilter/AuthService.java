package com.example.AuthFilter;

import com.example.Controller.AuthenticationRequest;
import com.example.Controller.AuthenticationResponse;
import com.example.Controller.RegisterRequest;
import com.example.Model.Organization;
import com.example.Model.Token;
import com.example.Model.TokenType;
import com.example.Model.User;
import com.example.Repository.OrganizationRepo;
import com.example.Repository.TokenRepo;
import com.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service

public class AuthService {

    private final UserRepository userRepository;

    private final  PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public final AuthenticationManager authenticationManager;

    private final OrganizationRepo organizationRepo;

    private final TokenRepo tokenRepo;
    @Autowired
    public  AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, OrganizationRepo organizationRepo, TokenRepo tokenRepo){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
        this.organizationRepo = organizationRepo;
        this.tokenRepo = tokenRepo;
    }
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user= new User();
        user.setFirstname(registerRequest.getFirstname());
        user.setLastname(registerRequest.getLastname());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        Organization organization=organizationRepo.findById(registerRequest.getOrgId()).orElse(null);
        user.setOrganization(organization);

        var savedUser= userRepository.save(user);
        var jwtToken=jwtService.generateToken(user);
        revokeAllTokens(user);
        saveUserToken(user,jwtToken);
        return AuthenticationResponse.builder().jwt(jwtToken).build();
    }
    private void revokeAllTokens(User user){
     var validToken=tokenRepo.findAllValidTokensByUser(user.getId());
     if(validToken.isEmpty())
         return;
     validToken.forEach(t -> {
         t.setRevoked(true);
         t.setExpired(true);
     });
         tokenRepo.saveAll(validToken);


    }
    private void saveUserToken(User user, String jwtToken) {
        Token token=new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepo.save(token);
    }

    public String authenticate(AuthenticationRequest registerRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            registerRequest.getUsername(),
                            registerRequest.getPassword())

            );
            var user = userRepository.findByUsername(registerRequest.getUsername()).orElseThrow(() -> new BadCredentialsException("User not found"));

            var jwtToken = jwtService.generateToken(user);
            revokeAllTokens(user);
            saveUserToken(user,jwtToken);
            return jwtToken;
        }
        catch(AuthenticationException e){
            return "Username or Password does not Match";
        }
               }
}

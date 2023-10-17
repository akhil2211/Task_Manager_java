package com.example.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

//import static com.example.Model.Permission.ADMIN_VIEWUSERS;
//import static com.example.Model.Permission.GM_VIEWTEAM;
//import static com.example.Model.UserRoles.*;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration {
    @Autowired
    private  JwtFilter jwtAuthFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private LogoutHandler logoutHandler;
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
//                        .requestMatchers("/api/v1/admin/***").hasAnyRole(ADMIN.name(), GM.name())
//                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/***").hasAnyAuthority(ADMIN_VIEWUSERS.name(),GM_VIEWTEAM.name())
                        .anyRequest().authenticated()
                ).sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout.addLogoutHandler(logoutHandler)
                        .logoutUrl("/api/v1/auth/logout")
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))

                ;


        return httpSecurity.build();

    }
}

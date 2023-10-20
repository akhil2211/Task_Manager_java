package com.example.Authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.example.Model.Permission.*;
import static com.example.Model.UserRoles.*;


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
                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
                        .requestMatchers("/api/v1/gm/**").hasRole(GM.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/gm/**").hasAuthority(GM_READ.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/gm/**").hasAuthority(GM_UPDATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/gm/**").hasAuthority(GM_CREATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/gm/**").hasAuthority(GM_DELETE.name())
                        .requestMatchers("/api/v1/pm/**").hasRole(PM.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/pm/**").hasAuthority(PM_READ.name())
                        .requestMatchers(HttpMethod.PUT, "/api/v1/pm/**").hasAuthority(PM_UPDATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/pm/**").hasAuthority(PM_CREATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/pm/**").hasAuthority(PM_DELETE.name())
                        .requestMatchers("/api/v1/project/task/create").hasAnyRole(PM.name(),GM.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/project/task/create").hasAnyAuthority(PM_CREATE.name(), GM_CREATE.name())
                        .requestMatchers("/api/v1/project/task/{taskId}/editTask").hasAnyRole(PM.name(),GM.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/project/{taskId}/editTask").hasAnyAuthority(PM_CREATE.name(), GM_CREATE.name())
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

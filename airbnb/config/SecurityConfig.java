package com.airbnb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().disable();

        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/users/add-user", "/api/v1/users/login")
                .permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}

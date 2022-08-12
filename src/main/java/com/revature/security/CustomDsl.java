package com.revature.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

    private String secret;

    @Override
    public void init(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                // enabling h2 console
                .and()
                .headers().frameOptions().disable()
                // end of h2 console block
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager);
        authenticationFilter.setSecret(secret);
        http.addFilter(authenticationFilter);

        CustomAuthorizationFilter authorizationFilter = new CustomAuthorizationFilter();
        authorizationFilter.setSecret(secret);
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public CustomDsl setJwtSecret(String secret) {
        this.secret = secret;
        return this;
    }
}
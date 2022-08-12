package com.revature.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// https://docs.spring.io/spring-security/reference/5.8.0-M1/servlet/configuration/java.html#_multiple_httpsecurity
// https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
// https://youtu.be/VVn9OG9nfH0
// https://jwt.io/

@EnableWebSecurity
public class SecurityConfig {

    @Value("${JWT_SECRET}")
    private String secret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.apply(new CustomDsl()).setJwtSecret(secret);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
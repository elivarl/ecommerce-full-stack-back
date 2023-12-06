package com.icodeap.ecommerce.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf( csrf-> csrf.disable()).authorizeHttpRequests(
                aut -> aut.requestMatchers("api/v1/admin/categories/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/admin/products/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/orders/**").hasRole("USER")
                        .requestMatchers("api/v1/payments/**").hasRole("USER")
                        .requestMatchers("api/v1/home/**").permitAll()
                        .requestMatchers("api/v1/security/**").permitAll().anyRequest().authenticated()
        );

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }


}

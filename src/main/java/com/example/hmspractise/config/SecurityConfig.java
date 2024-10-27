package com.example.hmspractise.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

        http.cors().disable().csrf().disable();

        http.authorizeHttpRequests().
                requestMatchers("api/v1/auth/signUser","api/v1/auth/signOwner")
                .permitAll();

        http.authorizeHttpRequests().requestMatchers("/api/country")
                .hasAnyRole("OWNER","ADMIN").anyRequest().authenticated();

        /* http.authorizeHttpRequests()
                .requestMatchers("/api/country").hasAnyRole("OWNER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin(Customizer.withDefaults());

        return http.build();*/


        return http.formLogin(Customizer.withDefaults()).build();

    }
}

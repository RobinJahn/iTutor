package com.example.itutor.config;

import com.example.itutor.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private HandlerMappingIntrospector handlerMappingIntrospector;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/home")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/students/signup")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/experts/signup")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/experts/add/process")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}
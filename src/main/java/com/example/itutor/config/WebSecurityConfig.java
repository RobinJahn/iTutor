package com.example.itutor.config;

import com.example.itutor.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private HandlerMappingIntrospector handlerMappingIntrospector;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(new AntPathRequestMatcher("/api/**"))
                .authorizeHttpRequests((requests) -> requests
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/home")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/error")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/students/signup")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/experts/signup")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/researchers/signup")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/students/add/process")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/experts/add/process")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/researchers/add/process")).permitAll()

                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/students/motivation")).hasAuthority("STUDENT")
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/experts/guideline")).hasAuthority("EXPERT")
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/statistics/**")).hasAuthority("RESEARCHER")

                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/css/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/js/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(handlerMappingIntrospector, "/image/**")).permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
        ;

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}
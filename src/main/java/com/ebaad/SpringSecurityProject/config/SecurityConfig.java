package com.ebaad.SpringSecurityProject.config;

import com.ebaad.SpringSecurityProject.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    // Setting up the authorization
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/home", "/api/**").permitAll();
                    request.requestMatchers("/user/**").hasRole("USER");
                    request.requestMatchers("/admin/**").hasRole("ADMIN");
                    request.anyRequest().authenticated();
                })
                .formLogin(formLogin -> formLogin.permitAll()) // Allow everyone to access the login page

//                In case for custom login page design
//                .formLogin(httpSecurityFormLoginConfigurer -> {
//                    httpSecurityFormLoginConfigurer.loginPage("/login").permitAll();
//                })
                .csrf(csrf -> csrf.disable())
                .build();
    }

    // Loading the user from memory, basically setting up the authentication credentials for users
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.builder()
//                .username("ebaad")
//                .password("$2a$12$Gewg4VEhcjpUckBxMKm9le8BgsKFWnCSrl6dh2JOlXx3cFLPVBhBa")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("sarim")
//                .password("$2a$12$J34elX4Ydt3GU6G27IsQs.eTnzxBnwo5AOKHKA0xRjK417TlmBhEW")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    // Loading the user from the database
    @Bean
    public UserDetailsService userDetailsService() {
        return myUserDetailsService;
    }

    // We need to tell the authentication provider / UserDetailsService that what kind of
    // password encoding or authentication we are going to use
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // for explicitly loading user from DB and use it for authentication
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    // Type of password encoding we are using
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


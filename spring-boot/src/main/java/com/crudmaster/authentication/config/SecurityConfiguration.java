package com.crudmaster.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.crudmaster.authentication.exceptionHandeling.RestAuthenticationEntryPoint;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    private RestAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.csrf().disable();
        http.authorizeHttpRequests(authenticate -> authenticate.requestMatchers("/user/**").permitAll()
        	.requestMatchers("/admin/**").permitAll()
                .anyRequest().authenticated()
                );
        http.httpBasic().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
       
	return http.build();
    }
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
    }

}

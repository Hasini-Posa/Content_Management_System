package com.example.cms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private CutomUserDetailsService customUserDetails;
	
	public SecurityConfig(CutomUserDetailsService customUserDetails) {
		this.customUserDetails = customUserDetails;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(customUserDetails);
		return daoAuthenticationProvider;
	}
	
	@Bean
	SecurityFilterChain chain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable()).
		authorizeHttpRequests(auth -> 
		auth.requestMatchers("/users/register").permitAll().anyRequest().authenticated()).
		formLogin(Customizer.withDefaults()).build();
	}
}

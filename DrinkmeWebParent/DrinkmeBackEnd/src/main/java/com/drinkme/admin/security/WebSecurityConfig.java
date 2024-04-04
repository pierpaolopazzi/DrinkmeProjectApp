package com.drinkme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
	
	@Bean
	UserDetailsService userDetailsService() {
		return new DrinkmeUserDetailsService();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
	
		return authProvider;
	}
	
	
	@Bean
	SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider());
		
		
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/utenti/**").hasAuthority("Admin")
				.requestMatchers("/categorie/**").hasAnyAuthority("Admin", "Cameriere")
				.anyRequest().authenticated()
				)  // Lambda DSL Syntax
				.formLogin(form -> form
						.loginPage("/login")
						.usernameParameter("email")
						.permitAll())
				.logout(logout -> logout.permitAll())
				
				.rememberMe(rem -> rem
						.key("AbcDefgHijKlmnOpqrs_1234567890")
						.tokenValiditySeconds(7 * 24 * 60 * 60));
		
		return http.build();
	}
	
	@Bean
	WebSecurityCustomizer configureWebSecurity() throws Exception {
		return (web) -> web.ignoring().requestMatchers("/images/**", "/js/**", "/webjars/**");
	}

/*
	@Bean
	SecurityFilterChain configureHttpSecurity(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
		
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
*/
}

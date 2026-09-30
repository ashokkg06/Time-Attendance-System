package com.wipro.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception { 
		http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:4200")); // Allow frontend origin
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
            config.setAllowCredentials(true);
            return config;
        }))
		.csrf(csrf -> csrf.disable())
		.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(authorize -> authorize
			.requestMatchers("/api/users/login", "/api/users/register", "/swagger-ui/**", "/v3/**").permitAll()
			.anyRequest().authenticated());
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return  http.build(); 
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{ 
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder(){ 
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	public CorsFilter corsFilter() {
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true); // Allow credentials like cookies, etc.
	    config.addAllowedOriginPattern("*"); // Or specific origin
	    config.addAllowedHeader("*"); // Allow all headers or specify headers like "Authorization"
	    config.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, etc.)
	    config.addExposedHeader("Authorization"); // Allow exposing headers to frontend
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}

}

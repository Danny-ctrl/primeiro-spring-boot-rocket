package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		return http
			.authorizeHttpRequests(authorizeHttpRequests())
			.sessionManagement(session())
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable)
			.build();
	}

	private Customizer<SessionManagementConfigurer<HttpSecurity>> session() {
		return s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	private Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> authorizeHttpRequests() {
		return authorization -> authorization.anyRequest().permitAll();
	}
}

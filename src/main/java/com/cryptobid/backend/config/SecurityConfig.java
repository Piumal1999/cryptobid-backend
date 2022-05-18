package com.cryptobid.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication()
		//		.withUser("admin")
		//		.password("{noop}pass") // Spring Security 5 requires specifying the password storage format
		//		.roles("ADMIN")
		//		.and()
		//		.withUser("user")
		//		.password("{noop}pass2")
		//		.roles();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//.antMatchers("/api/admin/**").hasRole("ADMIN")
				//.anyRequest().authenticated()
				.anyRequest().permitAll()
				.and()
				.httpBasic();
	}
}

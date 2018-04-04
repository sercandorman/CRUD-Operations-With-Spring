package com.crudwithspring.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select USERNAME, PASSWORD, 1 as enabled from USER_AND_ROLE where USERNAME=?")
				.authoritiesByUsernameQuery("select USERNAME, ROLE from USER_AND_ROLE where USERNAME=?");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
		.antMatchers("/").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
		.antMatchers("/add").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/modify").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/update").access("hasRole('ROLE_ADMIN')")
		.antMatchers("/delete").access("hasRole('ROLE_ADMIN')").and()
				.formLogin().loginProcessingUrl("/login").loginPage("/login").defaultSuccessUrl("/")
				.failureUrl("/login?error=true")
				.usernameParameter("username").passwordParameter("password").and()
				.logout()
				.logoutUrl("/logout") 
				.logoutSuccessUrl("/login")
	            .and().csrf().disable();
	}
}
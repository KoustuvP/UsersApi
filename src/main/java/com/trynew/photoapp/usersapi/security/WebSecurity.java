package com.trynew.photoapp.usersapi.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.trynew.photoapp.usersapi.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	Environment env;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		System.out.println("ip:>>>>>>"+env.getProperty("gateway-ip"));
		//http.authorizeRequests().antMatchers("/**").permitAll();
			http.authorizeRequests().antMatchers("/**").hasIpAddress("192.168.43.216")
		    .and()
		    .addFilter(getAuthFilter());
		http.headers().frameOptions().disable();
	}

	private AuthFilter getAuthFilter() throws Exception {
		AuthFilter authFilter= new AuthFilter(userService, env, authenticationManager());
		//authFilter.setAuthenticationManager(authenticationManager());
		authFilter.setFilterProcessesUrl("/users/auth");
		return authFilter;
	} 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userService).passwordEncoder(bCryptEncoder);
	}
}

package com.trynew.photoapp.usersapi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trynew.photoapp.usersapi.service.UserService;
import com.trynew.photoapp.usersapi.shared.dto.UserDto;
import com.trynew.photoapp.usersapi.ui.model.AuthRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthFilter extends UsernamePasswordAuthenticationFilter {
	
	
	private UserService userService;
	private Environment env;


	AuthFilter(UserService userService,Environment env, AuthenticationManager authenticationManager){
		super.setAuthenticationManager(authenticationManager);
		this.userService=userService;
		this.env=env;
		
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		  
		
		try {
			AuthRequestModel credential = new ObjectMapper().readValue(request.getInputStream(), AuthRequestModel.class);
			return getAuthenticationManager()
					.authenticate(
							new UsernamePasswordAuthenticationToken(
									credential.getEmail(), 
									credential.getPassword(), 
									new ArrayList<GrantedAuthority>() )
							);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException();
		} 
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//super.successfulAuthentication(request, response, chain, authResult);
		String userName =((User) authResult.getPrincipal()).getUsername();
		UserDto userDto=userService.getUser(userName);
		String token=Jwts.builder()
		.setSubject(userDto.getUserId())
		.setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration"))))
		.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
		.compact();
		response.addHeader("token", token);
	}

}

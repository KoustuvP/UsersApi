package com.trynew.photoapp.usersapi.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.trynew.photoapp.usersapi.shared.dto.UserDto;

public interface UserService extends UserDetailsService{

	public UserDto createUser(UserDto userDetails);

	public List<UserDto> getAllUsers();
	
	public UserDto getUser(String userName);
}

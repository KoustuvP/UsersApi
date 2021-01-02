package com.trynew.photoapp.usersapi.ui.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trynew.photoapp.usersapi.service.UserService;
import com.trynew.photoapp.usersapi.shared.dto.UserDto;
import com.trynew.photoapp.usersapi.ui.model.AuthRequestModel;
import com.trynew.photoapp.usersapi.ui.model.CreateUserResponseModel;
import com.trynew.photoapp.usersapi.ui.model.UserRequestModel;
import com.trynew.photoapp.usersapi.ui.model.GetUserResponseModel;

@RestController
@RequestMapping(path = "/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<GetUserResponseModel>> hi() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<UserDto> userDtos =userService.getAllUsers();
		List<GetUserResponseModel> userModels=userDtos.stream().map(user-> modelMapper.map(user,GetUserResponseModel.class )).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.CREATED).body(userModels);
	}
	
	@PostMapping("/auth")
	public String authenticate(@Valid @RequestBody AuthRequestModel authDetails) {
		return "hi";
	}
	
	@PostMapping()
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestModel userRequestDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = modelMapper.map(userRequestDetails, UserDto.class);
		UserDto createdUser=this.userService.createUser(userDto);
		CreateUserResponseModel responseUser = modelMapper.map(createdUser,CreateUserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}

}

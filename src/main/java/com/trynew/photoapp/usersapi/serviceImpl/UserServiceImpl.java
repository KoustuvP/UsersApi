package com.trynew.photoapp.usersapi.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.trynew.photoapp.usersapi.data.UserEntity;
import com.trynew.photoapp.usersapi.data.UserRepository;
import com.trynew.photoapp.usersapi.service.UserService;
import com.trynew.photoapp.usersapi.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository=userRepository;
		this.bCryptPasswordEncoder=bCryptPasswordEncoder;
	} 
	
	@Override
	public List<UserDto> getAllUsers() {
		// TODO Auto-generated method stub
		List<UserEntity> userEntities = userRepository.findAll();
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		//List<UserEntity> userEntity = modelMapper.map(userDTOList, List<UserEntity>.class);
		List<UserDto> userDtos=userEntities.stream().map(entity-> modelMapper.map(entity,UserDto.class )).collect(Collectors.toList());
		return userDtos;
	}
	
	
	@Override
	public UserDto createUser(UserDto userDetails) {

		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(this.bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		UserEntity createdEntity=this.userRepository.save(userEntity);
		UserDto createdUserDto=modelMapper.map(createdEntity,UserDto.class);
		return createdUserDto;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity userEntity=userRepository.findByEmail(username);
		if(userEntity==null) throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),true,true,true,true,new ArrayList<GrantedAuthority>());
	}

	@Override
	public UserDto getUser(String userName) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity=this.userRepository.findByEmail(userName);
		UserDto userDto=modelMapper.map(userEntity,UserDto.class);
		return userDto;
	}

}

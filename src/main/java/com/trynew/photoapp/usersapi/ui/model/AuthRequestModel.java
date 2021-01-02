package com.trynew.photoapp.usersapi.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthRequestModel {

	@NotNull(message="Email is required")
	@Email
	private String email;
	
	@NotNull(message="Password is required")
	@Size(min=8,max=16,message="Must be between 8 to 16")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

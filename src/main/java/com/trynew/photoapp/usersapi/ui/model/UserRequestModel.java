package com.trynew.photoapp.usersapi.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestModel {

	@NotNull(message="Field firstName is required")
	@Size(min=3,message="Field firstName must be greater than or equal to three characters")
	private String firstName;
	
	@NotNull(message="Field ladtName is required")
	@Size(min=3,message="Field firstName must be greater than or equal to three characters")
	private String LastName;
	
	@NotNull(message="Field email is required")
	@Email
	private String email;
	
	@NotNull(message="Field password is required")
	@Size(min=8,max=16,message="Field password must be between eight and sixteen characters")
	private String password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
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

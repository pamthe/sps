package com.capgemini.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserEmail {
	
	@NotNull 
	@NotEmpty(message = "Please provide the email id") 
	@Email(message = "Please provide the valid email id")
    @Size(max = 30, message = "email should not be more than 30 charaters")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

package com.capgemini.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Friend {
	
	@NotNull 
	@NotEmpty(message = "Please provide requestor email id.") 
	@Email(message = "Give valid requestor email id.")
	@Size(max = 30, message = "Requestor email should not be more than 5 charaters")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

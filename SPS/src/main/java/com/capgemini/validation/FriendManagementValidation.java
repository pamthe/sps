package com.capgemini.validation;

import org.springframework.stereotype.Component;

@Component
public class FriendManagementValidation {
   
	String status;
	String message;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String errorDescription) {
		this.message = errorDescription;
	}
	
	public FriendManagementValidation() {
		
	}
	
	public FriendManagementValidation(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	
}

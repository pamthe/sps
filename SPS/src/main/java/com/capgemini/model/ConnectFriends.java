package com.capgemini.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConnectFriends {
	
	@NotNull 
	@NotEmpty(message = "Please provide requestor email id.") 
	@Email(message = "Give valid requestor email id.")
	@Size(max = 30, message = "Requestor email should not be more than 5 charaters")
	String requestor;

	@NotNull 
	@NotEmpty(message = "Please provide traget email id.") 
	@Email(message = "Give valid target email id.")
	@Size(max = 30, message = "Target email should not be more than 5 charaters")
	String target;

	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}

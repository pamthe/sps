package com.capgemini.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EmailRecievesUpdatesBean {

	@NotNull 
	@NotEmpty(message = "Sender email should not be empty.") 
	@Email(message = "Give valid sender email id")
    @Size(max = 30, message = "Sender email should not be more than 30 charaters")
	private String sender;
	@NotNull
	@NotEmpty(message = "Message should not be empty")
	private String text;
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
}

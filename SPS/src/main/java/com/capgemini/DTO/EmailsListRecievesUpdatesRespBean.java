package com.capgemini.DTO;

import java.util.ArrayList;
import java.util.List;

public class EmailsListRecievesUpdatesRespBean {

	
	public String status;
	
	public List<String> friends = new ArrayList<String>();
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	
}

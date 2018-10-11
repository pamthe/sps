package com.capgemini.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.capgemini.model.UserEmail;
import com.capgemini.validation.FriendManagementValidation;

public class FriendMangmtRepo {
	FriendManagementValidation fmError;
	JdbcTemplate jdbcTemplate;
	@Autowired public FriendMangmtRepo(FriendManagementValidation fmError,JdbcTemplate jdbcTemplate) {
		this.fmError=fmError;
		this.jdbcTemplate=jdbcTemplate;
	}

	public boolean addNewFriendConnection(com.capgemini.model.UserRequest userReq){
		List<UserEmail> friends = userReq.getFriends();
		
		String requestor = friends.get(0).getEmail();
		String target = friends.get(1).getEmail();
        String query = "SELECT email FROM friendmanagement";
        List<String> emails =jdbcTemplate.queryForList(query,String.class);
        if(emails.contains(requestor) && emails.contains(target)){
        }else if(emails.contains(requestor)){
        	insertNewFriend(requestor, target);
        }else{
        	
        }
        return true;
		
	}
	
private void insertNewFriend(String requestor, String target){
		
		System.out.println("in request");
    	String sql = "INSERT into friendmanagement(Id, email, friend_list, subscription, text_message, updated_timestamp) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, new PreparedStatementSetter() {
			

			@Override
			public void setValues(PreparedStatement stmt) throws SQLException {
				stmt.setInt(1, 1009);
                stmt.setString(2, target);
                stmt.setString(3, requestor);
                stmt.setString(4, "");
                stmt.setString(5, "");
                stmt.setTimestamp(6, new java.sql.Timestamp(new Date().getTime()));
				
			}
        });
        
         jdbcTemplate.update("update friendmanagement " + " set friend_list = ?" + " where email = ?",
				new Object[] {
						target, requestor
		 });
	}
}

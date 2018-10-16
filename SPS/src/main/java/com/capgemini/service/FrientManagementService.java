package com.capgemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.DTO.CommonFriendsListRespBean;
import com.capgemini.DTO.EmailsListRecievesUpdatesRespBean;
import com.capgemini.DTO.FriendsList;
import com.capgemini.exceptionhandler.ResourceNotFoundException;
import com.capgemini.model.ConnectFriends;
import com.capgemini.repository.FriendManagementRepo;
import com.capgemini.validation.FriendManagementValidation;

@Service
public class FrientManagementService {

	@Autowired
	FriendManagementRepo friendMangmtRepo;
	@Autowired public FrientManagementService(FriendManagementRepo friendMangmtRepo) {
		this.friendMangmtRepo=friendMangmtRepo;
	} 

	public FriendManagementValidation addNewFriendConnection(com.capgemini.model.ConnectFriends userReq) {
		FriendManagementValidation fmResponse = friendMangmtRepo.addNewFriendConnection(userReq);
		return fmResponse;
	}

	public FriendManagementValidation subscribeTargetFriend(com.capgemini.model.ConnectFriends subscriber)throws ResourceNotFoundException {

		return friendMangmtRepo.subscribeTargetFriend(subscriber);

	}
	
	
	public FriendManagementValidation unSubscribeTargetFriend(ConnectFriends subscriber) throws ResourceNotFoundException {
		return friendMangmtRepo.unSubscribeTargetFriend(subscriber);
	}
	
public FriendsList getFriendList(com.capgemini.model.Friend friendListRequest) throws ResourceNotFoundException {
		
		return friendMangmtRepo.getFriendsList(friendListRequest);
		
		
	}
	
	

public CommonFriendsListRespBean retrieveCommonFriendList(String email1 ,String email2) throws ResourceNotFoundException {
		
		return friendMangmtRepo.retrieveCommonFriendList(email1,email2);
	}
	
public EmailsListRecievesUpdatesRespBean emailListRecievesupdates(com.capgemini.model.EmailRecievesUpdatesBean emailsList )throws ResourceNotFoundException {
	
	return friendMangmtRepo.emailListRecievesupdates(emailsList);
}




}

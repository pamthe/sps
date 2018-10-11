package com.capgemini.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.repository.FriendMangmtRepo;

@Service
public class FrientMangmtService {
	
FriendMangmtRepo friendMangmtRepo;
	
	@Autowired public FrientMangmtService(FriendMangmtRepo friendMangmtRepo) {
		this.friendMangmtRepo=friendMangmtRepo;
	}
	
	public boolean addNewFriendConnection(com.capgemini.model.UserRequest userReq) {
		boolean flag = friendMangmtRepo.addNewFriendConnection(userReq);
		return flag;
	}
	
}

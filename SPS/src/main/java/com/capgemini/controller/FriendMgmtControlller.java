package com.capgemini.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptionhandling.ResourceNotFoundException;
import com.capgemini.model.BaseResponse;
import com.capgemini.service.FrientMangmtService;
import com.capgemini.validation.FriendManagementValidation;


import com.capgemini.service.FrientMangmtService;
import com.capgemini.validation.FriendManagementValidation;

@RestController
@RequestMapping(value = "/sps")
public class FriendMgmtControlller {
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	FrientMangmtService frndMngtServc;
	FriendManagementValidation fmError;
	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";
	
	@Autowired public FriendMgmtControlller(FrientMangmtService frndMngtServc,FriendManagementValidation fmError) {
		this.frndMngtServc=frndMngtServc;
		this.fmError=fmError;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<BaseResponse> newFriendConnection(@Valid @RequestBody com.capgemini.model.UserRequest userReq, BindingResult result)throws ResourceNotFoundException {
		//public ResponseEntity<BaseResponse> newFriendConnection(@RequestBody com.capgemini.model.UserRequest userReq) {	
		LOG.info("====logger test =======");
		LOG.debug("====logger test =======");
		BaseResponse response = new BaseResponse();
		ResponseEntity<BaseResponse> re = null;
		try{
		boolean isNewfrndMangmReqSuccess = frndMngtServc.addNewFriendConnection(userReq)	;
		System.out.println("======="+isNewfrndMangmReqSuccess);
		LOG.info("======="+isNewfrndMangmReqSuccess);
		if(isNewfrndMangmReqSuccess){
		response.setStatus(SUCCESS_STATUS);
		   //response.setCode(CODE_SUCCESS);
		  } else {
		   response.setStatus(ERROR_STATUS);
		   //response.setCode(AUTH_FAILURE);
		  }
		re =  new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
		 
		}catch(Exception e) {
			
			re =  new ResponseEntity<BaseResponse>(response, HttpStatus.SERVICE_UNAVAILABLE);
			
		} 
		
		return re;


	}
}

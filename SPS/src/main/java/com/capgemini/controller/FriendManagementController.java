package com.capgemini.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exceptionhandler.ResourceNotFoundException;
import com.capgemini.model.CommonFriendsListRespBean;
import com.capgemini.model.ConnectFriends;
import com.capgemini.model.EmailsListRecievesUpdatesRespBean;
import com.capgemini.model.FriendsList;
import com.capgemini.service.FrientManagementService;
import com.capgemini.validation.FriendManagementValidation;


@RestController
@Validated
@RequestMapping(value = "/sps")
public class FriendManagementController {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	private static final String SUCCESS_STATUS = "Success";
	private static final String ERROR_STATUS = "error";
	private FrientManagementService frndMngtServc;
	private FriendManagementValidation fmValidation;

		@Autowired public FriendManagementController(FrientManagementService frndMngtServc,FriendManagementValidation fmError) {
			this.frndMngtServc=frndMngtServc;
			this.fmValidation=fmError;
		}

/**
 * This API connect 2 friends for given email IDs
 * @param userReq
 * @param results
 * @return
 * @throws ResourceNotFoundException
 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<FriendManagementValidation> newFriendConnection(@Valid @RequestBody com.capgemini.model.ConnectFriends userReq, BindingResult results)throws ResourceNotFoundException {
		LOG.info("newFriendConnection :: ");
		FriendManagementValidation fmResponse = new FriendManagementValidation();
		ResponseEntity<FriendManagementValidation> re = null;
		try{
			fmResponse = frndMngtServc.addNewFriendConnection(userReq)	;
			String isNewfrndMangmReqSuccess = fmResponse.getStatus();
			if(isNewfrndMangmReqSuccess.equalsIgnoreCase("Success")){
				fmResponse.setStatus(SUCCESS_STATUS);
				re =  new ResponseEntity<FriendManagementValidation>(fmResponse, HttpStatus.OK);
			} else {
				fmResponse.setStatus(ERROR_STATUS);
			}
			re =  new ResponseEntity<FriendManagementValidation>(fmResponse, HttpStatus.OK);

		}catch(Exception e) {
			LOG.error(e.getMessage());
			re =  new ResponseEntity<FriendManagementValidation>(fmResponse, HttpStatus.SERVICE_UNAVAILABLE);

		} 

		return re;


	}


	/**
	 * API to fetch friends of given email ID
	 * @param friendListRequest
	 * @param result
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = "/showFriendlist", method = RequestMethod.POST)
	public ResponseEntity<FriendsList> getFriendList(@Valid @RequestBody com.capgemini.model.Friend friendListRequest, BindingResult result)throws ResourceNotFoundException {
		LOG.info("--getFriendList :: " +friendListRequest.getEmail());
		FriendsList response = frndMngtServc.getFriendList(friendListRequest );
		ResponseEntity<FriendsList> responseEntity = null;
		try{
			if(response.getStatus() == SUCCESS_STATUS){
				response.setStatus(SUCCESS_STATUS);
				responseEntity = new ResponseEntity<FriendsList>(response, HttpStatus.OK);
			} else {
				response.setStatus(ERROR_STATUS);
				responseEntity = new ResponseEntity<FriendsList>(response, HttpStatus.BAD_REQUEST);
				//response.setCode(AUTH_FAILURE);
			}
		}catch(Exception e) {
			LOG.error(e.getMessage());
			responseEntity =  new ResponseEntity<FriendsList>(response, HttpStatus.SERVICE_UNAVAILABLE);

		} 
		return responseEntity;


	}


	/**
	 * get common friends between 2 users(provided their email IDs)
	 * @param commonFrndReq
	 * @return
	 * @throws ResourceNotFoundException
	 */
	
	@RequestMapping(value = "/showCommonfriends", method = RequestMethod.POST)

	public ResponseEntity<CommonFriendsListRespBean> getCommonFriendList(@Valid @RequestBody com.capgemini.model.CommonFriendsListBean  commonFrndReq) throws ResourceNotFoundException {	
		LOG.info("getCommonFriendList");
		ResponseEntity<CommonFriendsListRespBean> responseEntity = null;
		CommonFriendsListRespBean response = new CommonFriendsListRespBean();
		try{
			response = frndMngtServc.retrieveCommonFriendList(commonFrndReq.getFriends().get(0),commonFrndReq.getFriends().get(1) );


			if(response.getStatus() == SUCCESS_STATUS){
				response.setStatus(SUCCESS_STATUS);
				responseEntity = new ResponseEntity<CommonFriendsListRespBean>(response, HttpStatus.OK);
			} else {
				response.setStatus(ERROR_STATUS);
				responseEntity = new ResponseEntity<CommonFriendsListRespBean>(response, HttpStatus.BAD_REQUEST);
				//response.setCode(AUTH_FAILURE);
			}
		}catch(Exception e) {
			LOG.error(e.getMessage());
			responseEntity =  new ResponseEntity<CommonFriendsListRespBean>(response, HttpStatus.SERVICE_UNAVAILABLE);

		} 
		return responseEntity;
	}



	/**
	 * API to for requestor to subscribe updates of a target
	 * @param subscriber
	 * @param result
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/subscribe", method = RequestMethod.PUT)
	public ResponseEntity<FriendManagementValidation> subscribeFriend(@Valid @RequestBody com.capgemini.model.ConnectFriends subscriber, BindingResult result)throws ResourceNotFoundException {
		LOG.info("subscribeFriend ::");
		//Validation
		if(result.hasErrors()) {
			return handleValidation(result);
		}


		ResponseEntity<FriendManagementValidation> responseEntity = null;
		FriendManagementValidation fmv = new FriendManagementValidation();

		try {
			fmv =frndMngtServc.subscribeTargetFriend(subscriber);
			if(fmv.getStatus() == SUCCESS_STATUS) {
				responseEntity = new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.OK);
			}else {
				responseEntity = new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			LOG.error(e.getMessage());
			responseEntity =  new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.SERVICE_UNAVAILABLE);
		}

		return responseEntity;

	}
	
	
	
	
	
	/**
	 * API for requestor to  unsubscribe the requestor 
	 * @param subscriber
	 * @param result
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value="/unsubscribe", method = RequestMethod.PUT)
	public ResponseEntity<FriendManagementValidation> unSubscribeFriend(@Valid @RequestBody com.capgemini.model.ConnectFriends subscriber, BindingResult result)throws ResourceNotFoundException {
		//Validation
		ResponseEntity<FriendManagementValidation> responseEntity = null;
		boolean isValid=validateInput(subscriber);
		if(!isValid) {
			responseEntity = new ResponseEntity<FriendManagementValidation>(HttpStatus.BAD_REQUEST);
		}
		
		FriendManagementValidation fmv=null;
		try {
			fmv =frndMngtServc.unSubscribeTargetFriend(subscriber);
			if(fmv.getStatus() == SUCCESS_STATUS) {
				responseEntity = new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.OK);
			}else {
				responseEntity = new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			responseEntity = new ResponseEntity<FriendManagementValidation>(fmv, HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}


	private boolean validateInput(ConnectFriends subscriber) {
		// TODO Auto-generated method stub
		String requestor=subscriber.getRequestor();
		String target=subscriber.getTarget();
		if(requestor==null || target==null || requestor.equalsIgnoreCase(target)) {
			return false;
		}
		return true;
	}
	
	/**
	 * API to get all email address who can receive update from given email ID
	 * @param emailsList
	 * @param result
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@RequestMapping(value = "/showSubscriber", method = RequestMethod.PUT)
	public ResponseEntity<EmailsListRecievesUpdatesRespBean> emailListRecievesupdates(@Valid @RequestBody com.capgemini.model.EmailRecievesUpdatesBean emailsList, BindingResult result)throws ResourceNotFoundException {

		LOG.info("emailListRecievesupdates ::");

		ResponseEntity<EmailsListRecievesUpdatesRespBean> responseEntity = null;
		EmailsListRecievesUpdatesRespBean response = new EmailsListRecievesUpdatesRespBean();
		try{
			response = frndMngtServc.emailListRecievesupdates(emailsList );
			if(response.getStatus().toString() == SUCCESS_STATUS){
				response.setStatus(SUCCESS_STATUS);
				responseEntity = new ResponseEntity<EmailsListRecievesUpdatesRespBean>(response, HttpStatus.OK);
			} else {
				response.setStatus(ERROR_STATUS);
				responseEntity = new ResponseEntity<EmailsListRecievesUpdatesRespBean>(response, HttpStatus.BAD_REQUEST);
				//response.setCode(AUTH_FAILURE);
			}
		}catch(Exception e) {
			LOG.error(e.getMessage());
			responseEntity =  new ResponseEntity<EmailsListRecievesUpdatesRespBean>(response, HttpStatus.SERVICE_UNAVAILABLE);

		} 
		return responseEntity;

	}




	


	/**
	 * This method is used for client validation
	 * @param result
	 * @return
	 */
	private ResponseEntity<FriendManagementValidation> handleValidation(BindingResult result) {
		fmValidation.setStatus("Failed");
		if(result.getFieldError("requestor") != null && result.getFieldError("target") != null) {
			fmValidation.setMessage(result.getFieldError("requestor").getDefaultMessage()+" "+result.getFieldError("target").getDefaultMessage());
		}else if(result.getFieldError("target") != null) {
			fmValidation.setMessage(result.getFieldError("target").getDefaultMessage());
		}else{
			fmValidation.setMessage(result.getFieldError("requestor").getDefaultMessage());

		}
		return new ResponseEntity<FriendManagementValidation>(fmValidation, HttpStatus.BAD_REQUEST);

	}



}

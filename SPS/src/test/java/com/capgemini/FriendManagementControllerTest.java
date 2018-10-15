package com.capgemini;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import com.capgemini.controller.FriendManagementController;
import com.capgemini.exceptionhandler.ResourceNotFoundException;
import com.capgemini.model.CommonFriendsListBean;
import com.capgemini.model.CommonFriendsListRespBean;
import com.capgemini.model.Friend;
import com.capgemini.model.FriendsList;
import com.capgemini.model.ConnectFriends;
import com.capgemini.repository.FriendManagementRepo;
import com.capgemini.service.FrientManagementService;
import com.capgemini.validation.FriendManagementValidation;

@RunWith(SpringRunner.class)

@SpringBootTest

public class FriendManagementControllerTest {

//@Mock

	FriendManagementController friendManagementController;


	private ConnectFriends userRequest;

	private Friend friendListRequest;

	private CommonFriendsListBean commonFriendsListRequest;

	private CommonFriendsListRespBean commonFriendsListResponse;

	private FriendsList userFriendsListResponse;

//@InjectMocks

	@Mock

	private BindingResult result;

//@Mock

	FriendManagementValidation fmError;

	@Mock

	JdbcTemplate jdbcTemplate;

	@Mock

	FriendManagementRepo friendMangmtRepo;

	@InjectMocks

	FrientManagementService frndMngtServc;

	@Mock

	BindingResult bindingResult;

	@Before

	public void setUp() throws Exception {

		userRequest = new ConnectFriends();

		fmError = new FriendManagementValidation();

// friendMangmtRepo=new FriendMangmtRepo(fmError,jdbcTemplate);

//frndMngtServc=new FrientMangmtService(friendMangmtRepo);

		friendManagementController = new FriendManagementController(frndMngtServc, fmError);

		friendListRequest = new Friend();

		commonFriendsListRequest = new CommonFriendsListBean();

		commonFriendsListResponse = new CommonFriendsListRespBean();

		userFriendsListResponse = new FriendsList();

	}

	@Test

	public void test_null_requestor_unsubscribe() throws ResourceNotFoundException {

		userRequest.setTarget("ravi@gmail.com");

		userRequest.setRequestor(null);

		when(this.result.hasErrors()).thenReturn(false);

		ResponseEntity<FriendManagementValidation> responseEntity = friendManagementController
				.unSubscribeFriend(userRequest, result);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);

	}

	@Test

	public void test_null_subscriber_unsubscribe() throws ResourceNotFoundException {

		when(this.result.hasErrors()).thenReturn(false);

		userRequest.setRequestor("ravi@gmail.com");

		userRequest.setTarget(null);

		ResponseEntity<FriendManagementValidation> responseEntity = friendManagementController
				.unSubscribeFriend(userRequest, result);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);

	}

	@Test

	public void test_samesubreq_unsubscribe() throws ResourceNotFoundException {

		userRequest.setRequestor("ravi@gmail.com");

		userRequest.setTarget("ravi@gmail.com");

		when(this.result.hasErrors()).thenReturn(false);

		ResponseEntity<FriendManagementValidation> responseEntity = friendManagementController
				.unSubscribeFriend(userRequest, result);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);

	}

	@Test

	public void test_unSubscribe_success() throws ResourceNotFoundException {

		userRequest.setRequestor("ravi@gmail.com");

		userRequest.setTarget("arvi@gmail.com");

		when(this.result.hasErrors()).thenReturn(false);

		List<Object> obj = new ArrayList<Object>();

		fmError.setStatus("Success");

//String subscribers="ravi@gmail.com,arvi@gmail.com";

//when(this.jdbcTemplate.queryForObject("",obj.toArray(),String.class)).thenReturn(subscribers);

		when(frndMngtServc.unSubscribeTargetFriend(userRequest)).thenReturn(fmError);

		ResponseEntity<FriendManagementValidation> responseEntity = friendManagementController
				.unSubscribeFriend(userRequest, result);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}

	@Test

	public void test_addFriend_success() throws ResourceNotFoundException {

		fmError.setStatus("Success");

		userRequest.setRequestor("raga@gmail.com");

		userRequest.setTarget("raju@gmail.com");

		when(frndMngtServc.addNewFriendConnection(userRequest)).thenReturn(fmError);

		ResponseEntity<FriendManagementValidation> responseEntity = friendManagementController
				.newFriendConnection(userRequest, bindingResult);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}

	@Test

	public void test_getFriendList_success() throws ResourceNotFoundException {

		friendListRequest.setEmail("ranga@gmail.com");

		userFriendsListResponse.setStatus("Success");

		when(frndMngtServc.getFriendList(friendListRequest)).thenReturn(userFriendsListResponse);

		ResponseEntity<FriendsList> responseEntity = friendManagementController
				.getFriendList(friendListRequest, bindingResult);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}

	@Test

	public void test_getCommonFriendList_success() throws ResourceNotFoundException {

		List<String> friends = new ArrayList<String>();

		friends.add("ranga@gmail.com");

		friends.add("ranga1@gmail.com");

		commonFriendsListRequest.setFriends(friends);

		commonFriendsListResponse.setStatus("Success");

		when(frndMngtServc.retrieveCommonFriendList("ranga@gmail.com", "ranga1@gmail.com"))
				.thenReturn(commonFriendsListResponse);

		ResponseEntity<CommonFriendsListRespBean> responseEntity = friendManagementController
				.getCommonFriendList(commonFriendsListRequest);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}

	@Test

	public void test_subscribe_success() throws ResourceNotFoundException {

		userRequest.setRequestor("ravi@gmail.com");

		userRequest.setTarget("arvi@gmail.com");

		fmError.setStatus("Success");

		when(frndMngtServc.subscribeTargetFriend(userRequest)).thenReturn(fmError);

		ResponseEntity<FriendManagementValidation> responseEntity = friendManagementController
				.subscribeFriend(userRequest, bindingResult);

		assertThat(responseEntity.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);

	}

}

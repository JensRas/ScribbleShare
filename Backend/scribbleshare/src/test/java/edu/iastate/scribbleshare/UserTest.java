package edu.iastate.scribbleshare;


//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.startup.UserConfig;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.MockitoRule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.User.UserService;
import edu.iastate.scribbleshare.helpers.Status;
import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserController;


public class UserTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserController controller;

	@Mock
	UserRepository repo;

	@Mock
	User user;

	
    @BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getUserByIdTest() {
		
		when(controller.getUserByUsername("test")).thenReturn(new User("user", "password"));

		User user = userService.getUserByUsername("test");

		assertEquals("user", user.getUsername());
        //TODO pasaword is hashed
		//assertEquals("password", user.getPassword());
	}

	@Test
	public void getUsernameTest(){
		String username = userService.getUsername("string");
	}
	
	@Test
	public void getAllUsersTest() {
		Iterable<User> list = new ArrayList<User>();
		User userOne = new User("UserOne", "UserOnePassword");
		User userTwo = new User("UserTwo", "UserTwoPassword");
		User userThree = new User("UserThree", "UserThreePassword");

		((ArrayList<User>) list).add(userOne);
		((ArrayList<User>) list).add(userTwo);
		((ArrayList<User>) list).add(userThree);

		when(controller.getAllUsers()).thenReturn(list);

		Iterable<User> userList = userService.getAllUsers();

		assertEquals(3, ((List<User>) userList).size());
		verify(repo, times(1)).findAll();
	}
	



}



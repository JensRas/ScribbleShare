package edu.iastate.scribbleshare;


//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.User.UserService;
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
		
		when(controller.getUserByUsername("test")).thenReturn(new User("user", "password", "user"));

		User user = userService.getUserByUsername("test");

		assertEquals("user", user.getUsername());
        //TODO pasaword is hashed
		//assertEquals("password", user.getPassword());
	}

	@Test
	public void newUserTest(){
	
		userService.addUser("username", "password");

		verify(controller, times(1)).addNewUser(null, "username", "password");
	}

	@Test
	public void getAllUsersTest() {
		Iterable<User> list = new ArrayList<User>();
		User userOne = new User("UserOne", "UserOnePassword", "user");
		User userTwo = new User("UserTwo", "UserTwoPassword", "user");
		User userThree = new User("UserThree", "UserThreePassword", "user");

		((ArrayList<User>) list).add(userOne);
		((ArrayList<User>) list).add(userTwo);
		((ArrayList<User>) list).add(userThree);

		when(userService.getAllUsers()).thenReturn(list);

		Iterable<User> userList = userService.getAllUsers();

		assertEquals(3, ((List<User>) userList).size());
		verify(controller, times(1)).getAllUsers();
	}
	
	@Test
	public void getUserDataTest(){
		when(controller.getUserByUsername("test")).thenReturn(new User("username", "password", "user"));

		User user = userService.getUserByUsername("test");
		user.setIsBanned(false);
		user.setPermissionLevel("test");
		user.setIsMuted(false);
		
		assertEquals("test", user.getPermissionLevel());
		assertEquals(false, user.getIsBanned());
		assertEquals(false, user.getIsMuted());
	}

	

}



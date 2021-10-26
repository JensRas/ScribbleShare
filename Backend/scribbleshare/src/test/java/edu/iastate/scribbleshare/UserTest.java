package edu.iastate.scribbleshare;


//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import org.junit.Before;
//import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.User.UserService;
import edu.iastate.scribbleshare.User.User;


public class UserTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository repo;

    @BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAccountByIdTest() {
		when(repo.getUserByUsername("test")).thenReturn(new User("user", "password"));

		User user = userService.getUserByUsername("test");

		assertEquals("user", user.getUsername());
        //TODO pasaword is hashed
		//assertEquals("password", user.getPassword());
	}

    
	@Test
	public void getAllUsersTest() {
		List<User> list = new ArrayList<User>();
		User userOne = new User("UserOne", "UserOnePassword");
		User userTwo = new User("UserTwo", "UserTwoPassword");
		User userThree = new User("UserThree", "UserThreePassword");

		list.add(userOne);
		list.add(userTwo);
		list.add(userThree);

		when(repo.getAllUsers()).thenReturn(list);

		List<User> userList = userService.getAllUsers();

		assertEquals(3, userList.size());
		verify(repo, times(1)).getAllUsers();
	}

    



}



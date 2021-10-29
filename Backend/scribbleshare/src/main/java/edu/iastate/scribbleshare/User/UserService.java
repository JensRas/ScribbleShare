package edu.iastate.scribbleshare.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	private UserController controller;

	public User getUserByUsername(String username) {
		User user = controller.getUserByUsername(username);
		return user;
	}

	public Iterable<User> getAllUsers(){
		return controller.getAllUsers();
	}

	public void addUser(String username, String password){
		controller.addNewUser(username, password);
	}

}

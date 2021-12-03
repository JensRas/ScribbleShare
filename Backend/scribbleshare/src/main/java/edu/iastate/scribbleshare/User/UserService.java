package edu.iastate.scribbleshare.User;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserController controller;

	public User getUserByUsername(String username) {
		User user = controller.getUserByUsername(username);
		return user;
	}

	public Iterable<User> getAllUsers(){
		return controller.getAllUsers();
	}

	public void addUser(String username, String password){
		controller.addNewUser(null, username, password);
	}

}

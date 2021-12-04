package edu.iastate.scribbleshare.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.iastate.scribbleshare.Post.Post;

@Service
public class UserService {

	private UserController controller;

	@Autowired
	private UserRepository userRepository;

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

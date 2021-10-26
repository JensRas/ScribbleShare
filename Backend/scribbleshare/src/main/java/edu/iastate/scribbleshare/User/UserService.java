package edu.iastate.scribbleshare.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public User getUserByUsername(String username) {
		return repo.getUserByUsername(username);
	}

	public List<User> getAllUsers() {
	    return repo.getAllUsers();
	}

}

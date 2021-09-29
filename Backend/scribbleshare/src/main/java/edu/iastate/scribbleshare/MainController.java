package edu.iastate.scribbleshare;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.helpers.Security;
import edu.iastate.scribbleshare.exceptions.BadHashException;

@RestController
public class MainController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @PostMapping(path="/createNewUser")
    public @ResponseBody String addNewUser(@RequestParam String username, @RequestParam String password){
        //TODO add checking if username is allowed

        if(userRepository.findById(username).isPresent()){
          //handle invalid username
          return "username already exists";
        }

        User n = new User();
        n.setUsername(username);
        String hash = Security.generateHash(password);
        if(hash == null){
            throw new BadHashException();
        }
        n.setPassword(hash);

        userRepository.save(n);
        return "new user created";
    }

    @GetMapping(path="users")
    public @ResponseBody Iterable<User> getAllUsers() {
      return userRepository.findAll();
    }

    @GetMapping(path="users/{username}")
    public Optional<User> getUserByUsername(@PathVariable("username") String username){
      Optional<User> user = userRepository.findById(username);
      if(!user.isPresent()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username doesn't exist");
      }
      return user;
    }

    @GetMapping(path="login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password){
      if(!userRepository.findById(username).isPresent()){
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
      }
      //TODO add a better return here
      return "" + Security.checkHash(userRepository.findById(username).get().getPassword(), password); 
    }

    @GetMapping(path="test")
    public Iterable<User> testEndpoint(){
      return userRepository.queryExample("AbrahamHowell");
    }

}

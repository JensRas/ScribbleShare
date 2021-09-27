package edu.iastate.scribbleshare;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path="/getAllUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
      return userRepository.findAll();
    }

    @GetMapping(path="getUser/{username}")
    public Optional<User> getUserByUsername(@PathVariable("username") String username){
      //TODO add handling if username doesn't exist
      return userRepository.findById(username);
    }

    @GetMapping(path="test")
    public Iterable<User> testEndpoint(){
      return userRepository.queryExample("AbrahamHowell");
    }

}

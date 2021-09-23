package edu.iastate.scribbleshare;


import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.helpers.Security;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);


    // @PostMapping(path="/add")
    // public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email){
    //     User n = new User();
    //     n.setUsername(name);
    //     n.setEmail(email);
    //     userRepository.save(n);
    //     return "Saved";
    // }

    @PostMapping(path="/createNewUser")
    public @ResponseBody String addNewUser(@RequestParam String username, @RequestParam String password){
        //TODO add checking if username exists
        
        User n = new User();
        n.setUsername(username);
        String hash = Security.generateHash(password);
        if(hash == null){
            //TODO throw an error for bad hash
        }
        n.setPassword(hash);

        userRepository.save(n);
        return "new user created";
    }

    @GetMapping(path="/getAllUsers")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}

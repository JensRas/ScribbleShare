package edu.iastate.scribbleshare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.helpers.Security;
import edu.iastate.scribbleshare.Objects.User;
import edu.iastate.scribbleshare.Repository.UserRepository;
import edu.iastate.scribbleshare.exceptions.BadHashException;

@RestController
public class MainController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @PutMapping(path="/users/new")
    public @ResponseBody String addNewUser(@RequestParam String username, @RequestParam String password){
        //TODO add checking if username is allowed

        if(userRepository.findById(username).isPresent()){
          //handle invalid username
          return "username already exists";
        }
        userRepository.save(new User(username, password));
        return "new user created";
    }

    @GetMapping(path="/users")
    public @ResponseBody Iterable<User> getAllUsers() {
      return userRepository.findAll();
    }

    @GetMapping(path="/users/{username}")
    public @ResponseBody Optional<User> getUserByUsername(@PathVariable String username){
      Optional<User> user = userRepository.findById(username);
      if(!user.isPresent()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username doesn't exist");
      }
      return user;
    }

    @GetMapping(path="/users/login")
    public @ResponseBody String login(@RequestParam String username, @RequestParam String password){
      if(!userRepository.findById(username).isPresent()){
        return "false";
      }
      //TODO add a better return here. Now just returns "true" or "false"
      return "" + Security.checkHash(userRepository.findById(username).get().getPassword(), password); 
    }

    /*
    following vs followers

    If I follow somebody, I'm FOLLOWING them
    If somebody follows me, they are a FOLLOWER
    */
    @PutMapping(path="following")
    public @ResponseBody String addFollower(@RequestParam String followerUsername, @RequestParam String followingUsername){
      User follower = userRepository.findById(followerUsername).get();
      User following = userRepository.findById(followingUsername).get();

      //TODO add error handling if user(s) dont exist or duplicate follow requests (maybe not the lastone)
      //TODO remove the ability to follow yourself (lol)

      follower.getFollowing().add(following);
      userRepository.save(follower);
      return "";
    }

    @GetMapping(path="following")
    public @ResponseBody Set<User> getFollowing(@RequestParam String username){
      return userRepository.findById(username).get().getFollowing();
    }

    //TODO get followers (everybody following one specific user)
    @GetMapping(path="followers")
    public @ResponseBody Set<User> getFollowers(@RequestParam String username){
      return null;
    }

    @DeleteMapping(path="unfollow")
    public @ResponseBody void unfollowUser(@RequestParam String followerUsername, @RequestParam String followingUsername){
      //TODO 
      Optional<User> followerOptional = userRepository.findById(followerUsername);
      Optional<User> followingOptional = userRepository.findById(followingUsername);
      if(!followerOptional.isPresent() || !followingOptional.isPresent()){
        //TODO throw error where usernames aren't present
      }

      User follower = followerOptional.get();
      User following = followingOptional.get();

      //TODO check if usernames are in the following table

      follower.getFollowing().remove(following);
      userRepository.save(follower);

    }

}
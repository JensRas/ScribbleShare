package edu.iastate.scribbleshare.User;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.helpers.Security;
import edu.iastate.scribbleshare.helpers.Status;

@RestController
public class UserController {
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
    public @ResponseBody void addFollower(HttpServletResponse response, @RequestParam String followerUsername, @RequestParam String followingUsername){
      Optional<User> followerOptional = userRepository.findById(followerUsername);
      Optional<User> followingOptional = userRepository.findById(followingUsername);
      if(!followerOptional.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, followerUsername + " doesn't exist"); return;}
      if(!followingOptional.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, followingUsername + " doesn't exist"); return;}
      if(followerUsername.equals(followingUsername)){Status.formResponse(response, HttpStatus.BAD_REQUEST, "you can't follow yourself lol"); return;}

      User follower = followerOptional.get();
      User following = followingOptional.get();
      follower.getFollowing().add(following);
      userRepository.save(follower);
      Status.formResponse(response, HttpStatus.CREATED, follower.getUsername() + " sucessfully followed " + following.getUsername());
    }

    @GetMapping(path="following")
    public @ResponseBody Set<User> getFollowing(HttpServletResponse response, @RequestParam String username){
      Optional<User> userOptional = userRepository.findById(username);
      if(!userOptional.isPresent()){
        Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist");
        return null;
      }
      return userRepository.findById(username).get().getFollowing();
    }

    @GetMapping(path="followers")
    public @ResponseBody Set<User> getFollowers(HttpServletResponse response, @RequestParam String username){
      Optional<User> user = userRepository.findById(username);
      if(!user.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist"); return null;}
      return user.get().getFollowers();
    }

    @DeleteMapping(path="unfollow")
    public @ResponseBody void unfollowUser(HttpServletResponse response, @RequestParam String followerUsername, @RequestParam String followingUsername){
      Optional<User> followerOptional = userRepository.findById(followerUsername);
      Optional<User> followingOptional = userRepository.findById(followingUsername);
      if(!followerOptional.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, followerUsername + " doesn't exist"); return;}
      if(!followingOptional.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, followingUsername + " doesn't exist"); return;}

      User follower = followerOptional.get();
      User following = followingOptional.get();
      if(!follower.getFollowing().contains(following)){
        Status.formResponse(response, HttpStatus.NOT_FOUND, follower.getUsername() + " isn't following " + following.getUsername());
        return;
      }

      follower.getFollowing().remove(following);
      userRepository.save(follower);
      Status.formResponse(response, HttpStatus.CREATED, follower.getUsername() + " sucessfully unfollowed " + following.getUsername());
    }

}
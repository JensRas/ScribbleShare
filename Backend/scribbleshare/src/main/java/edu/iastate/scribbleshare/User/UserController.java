package edu.iastate.scribbleshare.User;

import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "UserController", description = "REST API relating to User Entity")
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @ApiOperation(value = "Add New User", response = String.class, tags= "Users")
    @PutMapping(path="/users/new")
    public @ResponseBody User addNewUser(HttpServletResponse response, @RequestParam String username, @RequestParam String password){
        if(userRepository.findById(username).isPresent()){
          //handle invalid username
          Status.formResponse(response, HttpStatus.BAD_REQUEST, username + " already exists"); 
          return null;
        }
        User user = new User(username, password, "user");
        userRepository.save(user);
        return user;
    }

    @ApiOperation(value = "Get All Users", response = Iterable.class, tags = "Users")
    @GetMapping(path="/users")
    public @ResponseBody Iterable<User> getAllUsers() {
      return userRepository.findAll();
    }

    @ApiOperation(value = "Get user by username", response = User.class, tags= "Users")
    @GetMapping(path="/users/{username}")
    public @ResponseBody User getUserByUsername(@PathVariable String username){
      Optional<User> optionalUser = userRepository.findById(username);
      if(!optionalUser.isPresent()){
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username doesn't exist");
      }
      return optionalUser.get();
    }

    @GetMapping(path="/userStats/{username}")
    public @ResponseBody String getUserStats(HttpServletResponse response, @PathVariable String username){
      Set<User> followingSet = getFollowing(response, username);
      Set<User> followersSet = getFollowers(response, username);

      int followers = followersSet.size();
      int following = followingSet.size();

      return "{followers: " + followers + ", following: " + following + "}";
    }

    @ApiOperation(value = "Log in User", response = User.class, tags= "Users")
    @GetMapping(path="/users/login")
    public @ResponseBody User login(HttpServletResponse response, @RequestParam String username, @RequestParam String password){
      Optional<User> optionalUser = userRepository.findById(username);
      if(!optionalUser.isPresent()){
        Status.formResponse(response, HttpStatus.NOT_FOUND, username + " not found"); 
        return null;
      }
      User user = optionalUser.get();
      
      if(Security.checkHash(user.getPassword(), password)){
        Status.formResponse(response, HttpStatus.ACCEPTED); 
        return user;
      }else{
        //return bad code
        Status.formResponse(response, HttpStatus.FORBIDDEN); 
        return null;
      }
    }

    /*
    following vs followers

    If I follow somebody, I'm FOLLOWING them
    If somebody follows me, they are a FOLLOWER
    */
    @ApiOperation(value = "Add a follower", response = Void.class, tags= "Users")
    @PutMapping(path="following")
    public @ResponseBody void addFollower(HttpServletResponse response, @RequestParam String followerUsername, @RequestParam String followingUsername){
      Optional<User> followerOptional = userRepository.findById(followerUsername);
      Optional<User> followingOptional = userRepository.findById(followingUsername);
      if(!followerOptional.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, followerUsername + " doesn't exist"); return ;}
      if(!followingOptional.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, followingUsername + " doesn't exist"); return ;}
      if(followerUsername.equals(followingUsername)){Status.formResponse(response, HttpStatus.BAD_REQUEST, "you can't follow yourself lol"); return ;}

      User follower = followerOptional.get();
      User following = followingOptional.get();
      follower.getFollowing().add(following);
      userRepository.save(follower);
      Status.formResponse(response, HttpStatus.CREATED, follower.getUsername() + " sucessfully followed " + following.getUsername());
    }

    @GetMapping(path = "/isFollowing/{followerUsername}/{followingUsername}")
    public String isFollowing(HttpServletResponse response, @PathVariable String followerUsername, @PathVariable String followingUsername){
      Optional<User> followerOptional = userRepository.findById(followerUsername);
      Optional<User> followingOptional = userRepository.findById(followingUsername);
      
      User follower = followerOptional.get();
      User following = followingOptional.get();

      if(follower.getFollowing().contains(following)){
         return "{following: " + true + "}";
      }
      else{return "{following: " + false + "}";}      
    }

    @ApiOperation(value = "Get User Following", response = Set.class, tags= "Users")
    @GetMapping(path="following")
    public @ResponseBody Set<User> getFollowing(HttpServletResponse response, @RequestParam String username){
      Optional<User> userOptional = userRepository.findById(username);
      if(!userOptional.isPresent()){
        Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist");
        return null;
      }
      return userRepository.findById(username).get().getFollowing();
    }

    @ApiOperation(value = "Get User Followers", response = Set.class, tags= "Users")
    @GetMapping(path="followers")
    public @ResponseBody Set<User> getFollowers(HttpServletResponse response, @RequestParam String username){
      Optional<User> user = userRepository.findById(username);
      if(!user.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist"); return null;}
      return user.get().getFollowers();
    }

    @ApiOperation(value = "Unfollow User", response = Void.class, tags= "Users")
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
      }

      follower.getFollowing().remove(following);
      userRepository.save(follower);
      Status.formResponse(response, HttpStatus.CREATED, follower.getUsername() + " sucessfully unfollowed " + following.getUsername());
    }

    @GetMapping(path="/users/search/{search}")
    public @ResponseBody Iterable<User> searchUsers(@PathVariable String search) {
      if(search.equals(" ")){
        return userRepository.findAll();
      }

      ArrayList<User> r = new ArrayList<>();
      for(User user: userRepository.findAll()){
        if(user.getUsername().toLowerCase().contains(search.toLowerCase())){
          r.add(user);
        }
      }

      return r;
    }

    @PostMapping(path="/users/ban/{username}")
    public User banUser(HttpServletResponse response, @PathVariable String username){
      Optional<User> userOptional = userRepository.findById(username);
      if(!userOptional.isPresent()){
        Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist");
        return null;
      }
      User user = userOptional.get();
      user.setIsBanned(true);
      userRepository.save(user);
      return user;
    }

    @PostMapping(path="/users/unban/{username}")
    public User unbanUser(HttpServletResponse response, @PathVariable String username){
      Optional<User> userOptional = userRepository.findById(username);
      if(!userOptional.isPresent()){
        Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist");
        return null;
      }
      User user = userOptional.get();
      user.setIsBanned(false);
      userRepository.save(user);
      return user;
    }

    @PostMapping(path="/users/mod/{username}")
    public User modUser(HttpServletResponse response, @PathVariable String username){
      Optional<User> userOptional = userRepository.findById(username);
      if(!userOptional.isPresent()){
        Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist");
        return null;
      }
      User user = userOptional.get();
      user.setPermissionLevel("moderator");
      userRepository.save(user);
      return user;
    }

    @PostMapping(path="/users/unmod/{username}")
    public User unmodUser(HttpServletResponse response, @PathVariable String username){
      Optional<User> userOptional = userRepository.findById(username);
      if(!userOptional.isPresent()){
        Status.formResponse(response, HttpStatus.NOT_FOUND, username + " doesn't exist");
        return null;
      }
      User user = userOptional.get();
      user.setPermissionLevel("user");
      userRepository.save(user);
      return user;
    }
}
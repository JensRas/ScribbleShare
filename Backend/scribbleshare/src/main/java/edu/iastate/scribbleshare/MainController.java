package edu.iastate.scribbleshare;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.helpers.Security;
import edu.iastate.scribbleshare.Objects.Follower;
import edu.iastate.scribbleshare.Objects.User;
import edu.iastate.scribbleshare.Repository.FollowingRepository;
import edu.iastate.scribbleshare.Repository.UserRepository;
import edu.iastate.scribbleshare.exceptions.BadHashException;

@RestController
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowingRepository followingRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @PutMapping(path="/users/new")
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

    @GetMapping(path="/test")
    public Iterable<User> testEndpoint(){
      return userRepository.queryExample("AbrahamHowell");
    }

    @PostMapping(path="/addfollower")
    public @ResponseBody String addNewFollowing(@RequestParam String user, @RequestParam String following){
      
      //TODO check if user is already following 
      if(followingRepository.queryFindByUsernameAndFollowing(user, following) == null){
        //handle invalid username
        return "already follows";
      }
      Follower f = new Follower();
      f.setUsername(user);
      f.setFollowing(following);
      followingRepository.save(f);

      return "followed ";
    }

    @GetMapping(path="allfollow")
    public @ResponseBody Iterable<Follower> getAllFollowers() {
      return followingRepository.findAll();
    }

    @GetMapping(path="follower/{username}")
    public @ResponseBody Iterable<Follower> getFollowersByUser(@PathVariable("username") String username){
      return followingRepository.queryUsers(username);
    }

    @GetMapping(path="following/{follower}")
    public @ResponseBody Iterable<Follower> getUsersByFollowing(@PathVariable("follower") String follower){
      return followingRepository.queryFollowers(follower);
    }

    @GetMapping(path="unfollow/{username}/{following}")
    public @ResponseBody void unfollowUser(@PathVariable("username") String username, @PathVariable("following") String following){
      //TODO error handling

      Follower followerToRemove = followingRepository.queryFindByUsernameAndFollowing(username, following);
      followerToRemove.setUsername(username);
      followerToRemove.setFollowing(following);

      followingRepository.delete(followerToRemove);
    }
}
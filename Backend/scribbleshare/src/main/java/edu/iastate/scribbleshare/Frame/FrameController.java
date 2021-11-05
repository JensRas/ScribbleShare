package edu.iastate.scribbleshare.Frame;

import java.util.Optional;

import javax.persistence.PostRemove;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostRepository;
import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.helpers.Status;

@RestController
public class FrameController {
    @Autowired
    private FrameRepository frameRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path="/frames/{postId}")
    public Iterable<Frame> getFramesForPost(HttpServletResponse response, @PathVariable int postId){
        //TODO error handling
        Post post = postRepository.findById(postId).get();
        return frameRepository.findByPost(post);
    }

    @PostMapping(path="/frames")
    public Frame createNewFrame(HttpServletResponse response, @RequestParam String username, @RequestParam int postId){
        Optional<User> optionalUser = userRepository.findById(username);
        if(!optionalUser.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "Username not found");
        }

        Optional<Post> optionalPost = postRepository.findById(postId);
        if(!optionalPost.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "Post id not found");
        }
        
        Post post = optionalPost.get();
        int lastIndex = post.getLastFrameIndex();

        Frame frame = new Frame(post, lastIndex + 1);
        frameRepository.save(frame);
        post.getFrames().add(frame);
        postRepository.save(post);
        
        return frame;
    }
}

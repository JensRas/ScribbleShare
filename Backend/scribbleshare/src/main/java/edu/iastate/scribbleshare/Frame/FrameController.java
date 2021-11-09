package edu.iastate.scribbleshare.Frame;

import java.util.Optional;

import javax.persistence.PostRemove;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostRepository;
import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.helpers.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(value = "FrameController", description = "REST API relating to Frame Entity")
@RestController
public class FrameController {
    @Autowired
    private FrameRepository frameRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @ApiOperation(value = "Get Frames for a Post by Post Id", response = Iterable.class, tags= "Frames")
    @GetMapping(path="/frames/{postId}")
    public Iterable<Frame> getFramesForPost(HttpServletResponse response, @PathVariable int postId){
        //TODO error handling
        Post post = postRepository.findById(postId).get();
        return frameRepository.findByPost(post);
    }

    @ApiOperation(value = "Create New Frame. Must specify index to prevent concurrency errors with multiple users.", response = Frame.class, tags= "Frames")
    @PostMapping(path="/frames")
    public Frame createNewFrame(HttpServletResponse response, @RequestParam String username, @RequestParam int postId, @RequestParam int index){
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

        if(lastIndex + 1 != index){
            //bad request, but possible duplicate request, so return last frame
            logger.info("got multi-index case: lastIndex = " + lastIndex + " index = " + index);
            return post.getFrames().get(lastIndex);
        }

        Frame frame = new Frame(post, lastIndex + 1);
        frameRepository.save(frame);
        post.getFrames().add(frame);
        postRepository.save(post);
        
        logger.info("created new frame: " + frame.getID());

        return frame;
    }

}

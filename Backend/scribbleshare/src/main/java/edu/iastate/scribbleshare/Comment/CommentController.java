package edu.iastate.scribbleshare.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.Frame.FrameRepository;
import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.helpers.Status;

@RestController
public class CommentController {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    FrameRepository frameRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    private static String uploadPath = "/uploadedFiles/";

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @PutMapping(path="/comment")
    public Comment addNewComment(HttpServletResponse response, @RequestParam("username") String username, @RequestParam("frameId") int frameId, @RequestParam("image") MultipartFile imageFile) throws IllegalStateException, IOException{
        Optional<User> optionalUser = userRepository.findById(username);
        if(!optionalUser.isPresent()){
            Status.formResponse(response, HttpStatus.BAD_REQUEST, "username: " + username + " not found!");
            return null;
        }
        
        Optional<Frame> optionalFrame = frameRepository.findById(frameId);
        if(!optionalFrame.isPresent()){
            Status.formResponse(response, HttpStatus.BAD_REQUEST, "frameId: " + frameId + " not found!");
            return null;
        }
        Frame frame = optionalFrame.get();

        String fullPath = httpServletRequest.getServletContext().getRealPath(uploadPath);

        if (!new File(fullPath).exists()){
            new File(fullPath).mkdir();
        }

        Comment comment = new Comment(username);
        comment.setFrame(frame);
        commentRepository.save(comment);
        frame.getComments().add(comment);
        frameRepository.save(frame);
        logger.info(comment.getFrame().toString());
        fullPath += "comment_" + comment.getID() + "_" + imageFile.getOriginalFilename();
        comment.setPath(fullPath);

        //write file to disk
        File tempFile = new File(fullPath);
        imageFile.transferTo(tempFile);
        
        commentRepository.save(comment);

        logger.info("created comment with id: " + comment.getID() + " and path: " + comment.getPath());
        return comment;
    }

    @GetMapping(path="/comment/{frameId}")
    public Iterable<Comment> getCommentsForFrame(HttpServletResponse response, @PathVariable int frameId){
        Optional<Frame> optionalFrame = frameRepository.findById(frameId);
        if(!optionalFrame.isPresent()){
            Status.formResponse(response, HttpStatus.BAD_REQUEST, "frameId: " + frameId + " not found!");
            return null;
        }
        return optionalFrame.get().getComments();
    }
}

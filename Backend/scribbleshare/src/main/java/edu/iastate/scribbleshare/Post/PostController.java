package edu.iastate.scribbleshare.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Comment.CommentRepository;
import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.Frame.FrameRepository;
import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.helpers.Status;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FrameRepository frameRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    static final int NEW_POST_FRAME_COUNT = 10; //TODO might be better to put this in a config file

    private static String uploadPath = "/uploadedFiles/";

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @GetMapping(path="/post")
    public @ResponseBody Iterable<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping(path="/post/{id}")
    public @ResponseBody Post getPost(HttpServletResponse response, @PathVariable int id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, "Post with id: " + id + " not found!"); return null;}
        return optionalPost.get();
    }

    @PutMapping(path="/post")
    public Post addNewPost(HttpServletResponse response, @RequestParam("username") String username, @RequestParam("image") MultipartFile imageFile) throws IllegalStateException, IOException{
        if(imageFile.isEmpty()){
            Status.formResponse(response, HttpStatus.BAD_REQUEST, "Empty file specified");
            return null;
        }

        String fullPath = httpServletRequest.getServletContext().getRealPath(uploadPath);

        if (!new File(fullPath).exists()){
            new File(fullPath).mkdir();
        }

        //create post and set path location
        Post post = new Post(username);
        postRepository.save(post); //must be saved to set the id properly
        fullPath += "post_" + post.getID() + "_" + imageFile.getOriginalFilename();
        post.setPath(fullPath);

        //write file to disk
        File tempFile = new File(fullPath);
        imageFile.transferTo(tempFile);

        for(Frame frame : createEmptyFrames(post, NEW_POST_FRAME_COUNT)){
            post.addFrame(frame);
        }

        postRepository.save(post);

        logger.info("created post with id: " + post.getID() + " and path: " + post.getPath());

        return post;
    }

    private Iterable<Frame> createEmptyFrames(Post post, int count){
        ArrayList<Integer> ids = new ArrayList<>();
        for(int i = 0; i < count; i++){
            Frame f = new Frame(post, i);
            frameRepository.save(f);
            ids.add(f.getID());
        }
        return frameRepository.findAllById(ids);
    }

    @GetMapping(path="/post/getHomeScreenPosts/{username}")
    public Iterable<Post> getHomeScreenPosts(HttpServletResponse response, @PathVariable String username){
        Optional<User> optionalUser = userRepository.findById(username);
        if(!optionalUser.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, "Username: " + username + " not found!"); return null;}
        Iterable<Post> posts = postRepository.getHomeScreenPosts(username);
        return posts;
    }

    @GetMapping(path="/post/{id}/image")
    public ResponseEntity<Resource> getPostImage(HttpServletResponse response, @PathVariable int id) throws IOException{
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, "Post with id: " + id + " not found!"); return null;}
        
        Post post = optionalPost.get();
        if(post.getPath() == null){
            Status.formResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Post image path is null");
            return null;
        }

        String pathStr = post.getPath();
        File file = new File(pathStr);
        if(!file.exists()){
            Status.formResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Post image path: " + pathStr + " not found!");
            return null;
        }

        String[] splitPath = pathStr.split("/");
        String fileName = splitPath[splitPath.length - 1];

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource data = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(data);
    }

    @DeleteMapping(path="/post/{id}")
    public String deletePost(HttpServletResponse response, @PathVariable int id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, "Post with id: " + id + " not found!"); return null;}
        Post post = optionalPost.get();
        return deletePost(post);
    }

    //deletes posts in a range of ids
    //WARNING don't use this with the app. It's just useful for deleting posts for the backend devs
    @DeleteMapping(path="/post/{id_start}/{id_end}")
    public String deletePostRange(HttpServletResponse response, @PathVariable int id_start, @PathVariable int id_end){
        int count = 0;
        String r = "";
        for(int i = id_start; i <= id_end; i++){
            Optional<Post> optionalPost = postRepository.findById(i);
            if(!optionalPost.isPresent()){
                r += "id: " + i + " doesn't exist\n";
            }else{
                r += deletePost(optionalPost.get()) + "\n";
                count++;
            }
        }
        return r + "Attempted to delete " + count + " posts";
    }

    //TODO this should probably be moved to a service but I'm too lazy rn...
    private String deletePost(Post post){
        //delete column from table
        postRepository.delete(post);
        
        //delete post's image
        String imagePath = post.getPath();
        if(!new File(imagePath).exists()){
            return "Unable to locate path: " + imagePath + " for stored post: " + post.getID() + ". Deleting anyway.";
        }
        new File(imagePath).delete();
        
        return "post: " + post.getID() + " deleted";
    }

    //reports if there are any posts that have missing image save files
    @GetMapping(path="/post/imageHealthCheck")
    public String deletePostRange(HttpServletResponse response){
        String r = "";
        for(Post post : postRepository.findAll()){
            String path = post.getPath();
            if(!new File(path).exists()){
                r += "path: " + path + " doesn't exist for post id: " + post.getID() + "\n";
            }
        }
        return r;
    }

}
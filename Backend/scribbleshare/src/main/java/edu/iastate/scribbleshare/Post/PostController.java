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
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.helpers.Status;

@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

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
        postRepository.save(post);

        //write file to disk
        File tempFile = new File(fullPath);
        imageFile.transferTo(tempFile);

        logger.info("created post with id: " + post.getID() + " and path: " + post.getPath());

        return post;
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
            Status.formResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Post image path: " + pathStr + "not found!");
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

}
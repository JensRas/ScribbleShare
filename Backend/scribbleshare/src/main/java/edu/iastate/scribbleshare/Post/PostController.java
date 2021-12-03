package edu.iastate.scribbleshare.Post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.comments.CommentLine;

import antlr.CommonAST;

import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iastate.scribbleshare.ScribbleshareApplication;
import edu.iastate.scribbleshare.Comment.Comment;
import edu.iastate.scribbleshare.Comment.CommentRepository;
import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.Frame.FrameRepository;
import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.helpers.GifSequenceWriter;
import edu.iastate.scribbleshare.helpers.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "PostController", description = "REST API relating to Post Entity")
@RestController
public class PostController {
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FrameRepository frameRepository;

    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * The path where posts are uploaded to relative to the application context
     */
    private static String uploadPath = "/uploadedFiles/";

    private static final Logger logger = LoggerFactory.getLogger(ScribbleshareApplication.class);

    @ApiOperation(value = "Get All Posts", response = Iterable.class, tags= "Post")
    @GetMapping(path="/post")
    public @ResponseBody Iterable<Post> getAllPosts(){
        return postRepository.findAll();
    }

    @ApiOperation(value = "Get Post by Id", response = Post.class, tags= "Post")
    @GetMapping(path="/post/{id}")
    public @ResponseBody Post getPost(HttpServletResponse response, @PathVariable int id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, "Post with id: " + id + " not found!"); return null;}
        return optionalPost.get();
    }

    @ApiOperation(value = "Add New Post", response = Post.class, tags= "Post")
    @PutMapping(path="/post")
    public Post addNewPost(HttpServletResponse response, @RequestParam("username") String username, @RequestParam("image") MultipartFile imageFile) throws IllegalStateException, IOException{
        Optional<User> optionalUser = userRepository.findById(username);
        if(!optionalUser.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "User not found");
            return null;
        }

        if(imageFile.isEmpty()){
            Status.formResponse(response, HttpStatus.BAD_REQUEST, "Empty file specified");
            return null;
        }

        User user = optionalUser.get();

        String fullPath = httpServletRequest.getServletContext().getRealPath(uploadPath);

        if (!new File(fullPath).exists()){
            new File(fullPath).mkdir();
        }

        //create post and set path location
        Post post = new Post(user);
        postRepository.save(post); //must be saved to set the id properly
        long unixTime = System.currentTimeMillis() / 1000L;
        fullPath += "post_" + post.getID() + "_" + unixTime + imageFile.getOriginalFilename();
        post.setPath(fullPath);

        //write file to disk
        File tempFile = new File(fullPath);
        imageFile.transferTo(tempFile);

        Frame frame = new Frame(post, 0);
        frameRepository.save(frame);

        post.addFrame(frame);
        postRepository.save(post);

        user.getPosts().add(post);
        userRepository.save(user);

        logger.info("created post with id: " + post.getID() + " and path: " + post.getPath());

        return post;
    }

    @ApiOperation(value = "Get Posts For Homescreen", response = Iterable.class, tags= "Post")
    @GetMapping(path="/post/getHomeScreenPosts/{username}")
    public Iterable<Post> getHomeScreenPosts(HttpServletResponse response, @PathVariable String username){
        Optional<User> optionalUser = userRepository.findById(username);
        if(!optionalUser.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, "Username: " + username + " not found!"); return null;}
        Iterable<Post> posts = postRepository.getHomeScreenPosts(username);
        return posts;
    }

    @ApiOperation(value = "Get Post by Id", response = ResponseEntity.class, tags= "Post")
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

    @ApiOperation(value = "Delete Post by Id", response = String.class, tags= "Post")
    @DeleteMapping(path="/post/{id}")
    public String deletePost(HttpServletResponse response, @PathVariable int id){
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){Status.formResponse(response, HttpStatus.NOT_FOUND, "Post with id: " + id + " not found!"); return null;}
        Post post = optionalPost.get();
        return deletePost(post);
    }

    //deletes posts in a range of ids
    //WARNING don't use this with the app. It's just useful for deleting posts for the backend devs
    @ApiOperation(value = "Delete Post(DEV USE ONLY)", response = String.class, tags= "Post")
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

    /**
     * Delete a post from the repository
     * @param post post to be deleted
     * @return a string describing the status of the post delete operation. Should be sent directly to the user
     */
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

    @ApiOperation(value = "Check for Post Errors (DEV ONLY)", response = String.class, tags= "Post")
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

    @GetMapping(path="/post/{id}/gif")
    public ResponseEntity<Resource> getPostGif(HttpServletResponse response, @PathVariable int id) throws IOException{
        Optional<Post> optionalPost = postRepository.findById(id);
        if(!optionalPost.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "post id: " + id + " not found!");
            return null;
        }
        Post post = optionalPost.get();

        //fill gifComments with a list of Comments whos images should be inserted into the gif
        ArrayList<Comment> gifComments = new ArrayList<>();
        for(Frame frame : post.getFrames()){
            List<Comment> comments = frame.getComments();
            if(comments.size() < 1) {continue;}
            Comment highestComment = comments.get(0);
            for(int i = 1; i < comments.size(); i++){
                if(comments.get(i).getLikeCount() > highestComment.getLikeCount()){
                    highestComment = comments.get(i);
                }
            }
            gifComments.add(highestComment);
        }

        if(gifComments.size() > 0){
            String firstImagePath = post.getPath();
            logger.info("firstImagePath: " + firstImagePath);
            String gifPath = firstImagePath.replace(".png", ".gif");
            BufferedImage first = ImageIO.read(new File(firstImagePath));
            ImageOutputStream output = new FileImageOutputStream(new File(gifPath));
            
            GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 250, true);
            writer.writeToSequence(first);
            
            for(Comment comment : gifComments){
                BufferedImage next = ImageIO.read(new File(comment.getPath()));
                writer.writeToSequence(next);
            }

            writer.close();
            output.close();

            String[] splitPath = gifPath.split("/");
            String fileName = splitPath[splitPath.length - 1];

            File file = new File(gifPath);

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName);
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");

            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource data = new ByteArrayResource(Files.readAllBytes(path));

            //TODO delete the gif file now?
            long fileLength = file.length();
            file.delete();
            
            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(fileLength)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(data);

        }else{
            //TODO better handle when there are no comments?
            return null;
        }
    }
        
    @PostMapping(path="/post/like")
    public Post likePost(HttpServletResponse response, @RequestParam int post_id, @RequestParam String username){
        Optional<Post> optionalPost = postRepository.findById(post_id);
        Optional<User> optionalUser = userRepository.findById(username);

        if(!optionalPost.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "Post with id: " + post_id + " not found!");
            return null;
        }

        if(!optionalUser.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "User with username: " + username + " not found!");
            return null;
        }

        Post post = optionalPost.get();
        User user = optionalUser.get();

        if(user.getLikedPosts().contains(post)){
            return post;
        }

        user.getLikedPosts().add(post);
        userRepository.save(user);
        post.getLikedUsers().add(user);
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);

        return post;

    }

    @DeleteMapping(path="/post/like")
    public Post removeLikePost(HttpServletResponse response, @RequestParam int post_id, @RequestParam String username){
        Optional<Post> optionalPost = postRepository.findById(post_id);
        Optional<User> optionalUser = userRepository.findById(username);

        if(!optionalPost.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "Post with id: " + post_id + " not found!");
            return null;
        }

        if(!optionalUser.isPresent()){
            Status.formResponse(response, HttpStatus.NOT_FOUND, "User with username: " + username + " not found!");
            return null;
        }

        Post post = optionalPost.get();
        User user = optionalUser.get();

        if(!user.getLikedPosts().contains(post)){
            return post;
        }

        user.getLikedPosts().remove(post);
        userRepository.save(user);
        post.getLikedUsers().remove(user);
        post.setLikeCount(post.getLikeCount() - 1);
        postRepository.save(post);

        return post;

    }

}
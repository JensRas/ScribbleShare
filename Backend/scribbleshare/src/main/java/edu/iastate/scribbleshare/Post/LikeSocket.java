package edu.iastate.scribbleshare.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.mysql.cj.x.protobuf.MysqlxCrud.Limit;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@ServerEndpoint(value = "/live/like/{username}")
public class LikeSocket {
    
    private static PostRepository postRepository;
    private static UserRepository userRepository;

    @Autowired
    public void setPostRepository(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(LikeSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @RequestParam("posts") String posts) throws IOException{
        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        //Get all post likes and send it to the user
        sendMessageToPArticularUser(username, getLikeCounts(posts.split(",")));
    }

    @OnMessage
	public void onMessage(Session session, String message) throws IOException {

		// Handle new messages
		logger.info("Entered into Message: Got Message:" + message);
		String username = sessionUsernameMap.get(session);

        //+ 162  <---- adds a like to post 162

        String[] args = message.split(" ");
        if(args.length != 2){
            logger.info("invalid onMessage: " + message);
            return;
        }
        String operator = args[0];
        String postId = args[1];

        Optional<Post> optionalPost = postRepository.findById(Integer.parseInt(postId));
        if(!optionalPost.isPresent()){
            logger.info("Unable to find post: " + postId);
        }

        Post post = optionalPost.get();
        User user = userRepository.findById(username).get();

        if(operator.equals("+")){
            if(user.getLikedPosts().contains(post)){
                //already liked post, returning 
                return;
            }

            user.getLikedPosts().add(post);
            userRepository.save(user);
            post.getLikedUsers().add(user);
            post.setLikeCount(post.getLikeCount() + 1);
            postRepository.save(post);

        }else if(operator.equals("-")){
            if(!user.getLikedPosts().contains(post)){
                return;
            }
    
            user.getLikedPosts().remove(post);
            userRepository.save(user);
            post.getLikedUsers().remove(user);
            post.setLikeCount(post.getLikeCount() - 1);
            postRepository.save(post);


        }else{
            logger.info("unknown operator");
        }

        broadcast(postId + ": " + post.getLikeCount());
	}


	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");

        // remove the user connection information
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}


    private void sendMessageToPArticularUser(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
	}


	private void broadcast(String message) {
		sessionUsernameMap.forEach((session, username) -> {
			try {
				session.getBasicRemote().sendText(message);
			} 
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }
		});
	}
	

  // Gets the Chat history from the repository
	private String getLikeCounts(String[] posts) {
        StringBuilder sb = new StringBuilder();
        
        for(String postId : posts){
            Optional<Post> optionalPost = postRepository.findById(Integer.parseInt(postId));
            if(!optionalPost.isPresent()){
                logger.info("post: " + postId + " not found!");
                continue;
            }
            sb.append(optionalPost.get().getLikeCount() + ",");
        }

        return sb.toString();
	}

}

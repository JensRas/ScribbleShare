package edu.iastate.scribbleshare.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserController;
import edu.iastate.scribbleshare.User.UserRepository;
import edu.iastate.scribbleshare.User.UserService;

import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@ServerEndpoint(value = "/live/like/{username}")
public class LikeSocket {
    
    private static PostRepository postRepository;
    private static UserRepository userRepository;
    private static PostController postController;

    @Autowired
    public void setPostRepository(PostRepository postRepository){
        LikeSocket.postRepository = postRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        LikeSocket.userRepository = userRepository;
    }

    @Autowired
    public void setPostController(PostController postController){
        LikeSocket.postController = postController;
    }


    // Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(LikeSocket.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException{
        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        // Get all post likes and send it to the user
        // sendMessageToParticularUser(username, getLikeCounts(posts.split(",")));
    }

    // public void test(String body, User user){
    //     Optional<Post> optionalPost = postRepository.findById(Integer.parseInt(body));
    //     if(!optionalPost.isPresent()){
    //         logger.info("Unable to find post: " + body);
    //     }

    //     Post post = optionalPost.get();
    //     Set<Post> test = user.getLikedPosts();
    //     boolean test2 = test.contains(post); //  <--- this line fails
    // }

    @OnMessage
	public void onMessage(Session session, String message) throws IOException {

		// Handle new messages
		String username = sessionUsernameMap.get(session);
		logger.info("Entered into Message: Got Message:" + message + " from username: " + username);

        if(message.equals("test")){
            logger.info("GOT TEST");
            return;
        }

        //+ 162  <---- adds a like to post 162

        String[] args = message.split(" ");
        if(args.length != 2){
            logger.info("invalid onMessage: " + message);
            return;
        }
        String operator = args[0];
        String body = args[1];

        User user = userRepository.findById(username).get();
        if(operator.equals("r")){ //get all posts like counts, body is a comma seperated list of post ids to return
            String[] posts = body.split(",");
            String r = "";
            for(String postId: posts){
                //get post
                Optional<Post> optionalPost = postRepository.findById(Integer.parseInt(postId));
                if(!optionalPost.isPresent()){
                    logger.info("Unable to find post: " + postId + " when reading posts with WS");
                    r += postId + ":-1,"; //if the frontend ever gets -1 like count, it means something went wrong
                    continue;
                }
                Post post = optionalPost.get();
                r +=  postId + ":" + postRepository.getPostLikeCount(post.getID()) + ",";
            }

            //send response to single user
            sendMessageToParticularUser(username, r);
            
        }else if(operator.equals("+")){ //add a like, body is post id
            Optional<Post> optionalPost = postRepository.findById(Integer.parseInt(body));
            if(!optionalPost.isPresent()){
                logger.info("Unable to find post: " + body);
            }

            Post post = optionalPost.get();

            logger.info("getLikedPosts: " + userRepository.getLikedPosts(username));
            if(userRepository.getLikedPosts(username).contains((Integer)post.getID())){
                //already liked post, returning 
                logger.info("already liked post, returning ");
                return;
            }

            userRepository.addLikedPost(username, post.getID());
            broadcast(body + ":" + postRepository.getPostLikeCount(post.getID()));

        }else if(operator.equals("-")){ //remove a like, body is post id
            Optional<Post> optionalPost = postRepository.findById(Integer.parseInt(body));
            if(!optionalPost.isPresent()){
                logger.info("Unable to find post: " + body);
            }
            Post post = optionalPost.get();

            if(!userRepository.getLikedPosts(username).contains((Integer)post.getID())){
                return;
            }
    
            postRepository.removeLikedPost(username, post.getID());
            broadcast(body + ":" + postRepository.getPostLikeCount(post.getID()));
        }else{
            logger.info("ERROR: unknown operator");
        }

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


    private void sendMessageToParticularUser(String username, String message) {
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

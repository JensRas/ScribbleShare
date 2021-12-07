package edu.iastate.scribbleshare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostController;
import edu.iastate.scribbleshare.Post.PostRepository;
import edu.iastate.scribbleshare.User.User;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {
    
    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostController postController;
    
    HttpServletResponse resp = new MockHttpServletResponse();

    @BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

    /**
     * Verify that the post controller gets the correct post when given the post id
     */
    @Test
    public void getPostByIdTest(){
        User user1 = new User("test1", "password", "user");
        Post post1 = new Post(user1);
        when(postRepository.findById(eq(1))).thenReturn(Optional.of(post1));

        Post post = postController.getPost(resp, 1);
        assertEquals(post.getUser().getUsername(), "test1");
        assertEquals(post.getLikeCount(), 0);
    }

    /**
     * Verify that a request which 
     */
    @Test
    public void getPostByMissingIdTest(){
        when(postRepository.findById(eq(1))).thenReturn(Optional.empty());

        Post post = postController.getPost(resp, 1);
        assertEquals(post, null);
        assertEquals(HttpStatus.NOT_FOUND.value(), resp.getStatus());
    }

    /**
     * Test that calling postController.getAllPosts() actually calls postRepository.findAll()
     */
    @Test
    public void getAllPostsTest(){
        ArrayList<Post> l = new ArrayList<>();
        User user1 = new User("test1", "password", "user");
        User user2 = new User("test2", "password", "user");
        User user3 = new User("test3", "password", "user");
        l.add(new Post(user1));
        l.add(new Post(user2));
        l.add(new Post(user3));
        when(postRepository.findAll()).thenReturn(l);

        Iterable<Post> posts = postController.getAllPosts();
        verify(postRepository, times(1)).findAll();
        int i = 0;
        for(Post post : posts){
            assertEquals(post, l.get(i));
            i++;
        }

    }

}

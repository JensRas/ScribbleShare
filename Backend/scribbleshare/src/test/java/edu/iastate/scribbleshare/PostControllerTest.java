package edu.iastate.scribbleshare;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
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
        Post post1 = new Post("test1");
        when(postRepository.findById(eq(1))).thenReturn(Optional.of(post1));

        Post post = postController.getPost(resp, 1);

        assertEquals(post.getUsername(), "test1");
        assertEquals(post.getLikeCount(), 0);
        assertEquals(post.getCommentCount(), 0);
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
        l.add(new Post("test1"));
        l.add(new Post("test2"));
        l.add(new Post("test3"));
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

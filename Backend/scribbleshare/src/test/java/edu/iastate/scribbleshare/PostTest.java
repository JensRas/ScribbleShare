package edu.iastate.scribbleshare;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostController;

@RunWith(MockitoJUnitRunner.class)
public class PostTest {

    @Mock
    PostController postController;

    @BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

    /**
     * Asserts that posts are created with the correct values such as the following.
     * The correct username (specified on post creation)
     * The defualt like count
     * The default comment count
     */
    @Test
    public void createPostTest(){
        Post post = new Post("test");
        assertEquals(post.getUsername(), "test");
        assertEquals(post.getLikeCount(), 0);
        assertEquals(post.getCommentCount(), 0);
    }

}

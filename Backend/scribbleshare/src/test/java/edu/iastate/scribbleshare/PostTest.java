package edu.iastate.scribbleshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostController;
import edu.iastate.scribbleshare.User.User;

@RunWith(MockitoJUnitRunner.class)
public class PostTest {

    @Mock
    PostController postController;

    @Mock
    Post post;

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
        post = new Post(new User("test", "test", "test"));
        assertEquals(post.getLikeCount(), 0);
        post.setLikeCount(1);
        assertEquals(post.getLikeCount(), 1);
    }

    @Test
    public void addFrameTest(){
        post = new Post(new User("test", "test", "test"));
        List<Frame> frames = new ArrayList<Frame>();
        Frame frame = new Frame (post, 0);
        frames.add(frame);
        post.addFrame(frame);
        assertEquals(post.getFrames(), frames);
    }
}

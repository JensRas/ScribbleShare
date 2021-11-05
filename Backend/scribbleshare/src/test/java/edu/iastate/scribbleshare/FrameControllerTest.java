package edu.iastate.scribbleshare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.Frame.FrameController;
import edu.iastate.scribbleshare.Frame.FrameRepository;
import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostController;
import edu.iastate.scribbleshare.Post.PostRepository;

@RunWith(MockitoJUnitRunner.class)
public class FrameControllerTest {
    
    @Mock
    FrameRepository frameRepository;

    @Mock
    PostRepository postRepository;

    @InjectMocks
    FrameController frameController;
    
    HttpServletResponse resp = new MockHttpServletResponse();

    @BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    public void getFramesByPostIdTest(){
        Post post = new Post();
        List<Frame> l = new ArrayList<>();
        l.add(new Frame(post, 0));
        l.add(new Frame(post, 1));
        l.add(new Frame(post, 2));
        l.add(new Frame(post, 3));
        when(frameRepository.findByPost(any(Post.class))).thenReturn(l);
        when(postRepository.findById(any(int.class))).thenReturn(Optional.of(post));

        int i = 0;
        for(Frame frame : frameController.getFramesForPost(resp, 0)){
            assertEquals(frame.getPost(), post);
            assertEquals(frame.getFrameIndex(), i);
            i++;
        }

        verify(frameRepository, times(1)).findByPost(any(Post.class));
    }

}

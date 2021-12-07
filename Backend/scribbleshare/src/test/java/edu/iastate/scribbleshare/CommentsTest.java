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
import org.springframework.web.multipart.MultipartFile;

import edu.iastate.scribbleshare.Comment.Comment;
import edu.iastate.scribbleshare.Comment.CommentController;
import edu.iastate.scribbleshare.Comment.CommentRepository;
import edu.iastate.scribbleshare.Frame.Frame;
import edu.iastate.scribbleshare.Frame.FrameController;
import edu.iastate.scribbleshare.Frame.FrameRepository;
import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostController;
import edu.iastate.scribbleshare.Post.PostRepository;
import edu.iastate.scribbleshare.User.User;

@RunWith(MockitoJUnitRunner.class)
public class CommentsTest {
    
    @Mock
    CommentRepository repo;

    @Mock
    CommentController controller;

    @Mock
    Comment comment;

    @Mock
    FrameController fController;

    HttpServletResponse resp = new MockHttpServletResponse();
    
    @BeforeEach
    public void init() {
		MockitoAnnotations.openMocks(this);
	}

    //Makes sure we arent looking for something that doesnt exist
    @Test
    public void getLikesFailingTest(){
        User user = new User("user", "password", "permissionLevel");
        comment = new Comment(user);

        controller.getIfPostLikedByUser(resp, comment.getID(), "test");
        verify(repo, times(0)).getCommentLikedByUser(comment.getID(), "test");
    }

    }


package edu.iastate.scribbleshare;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.mock.web.MockHttpServletResponse;

import edu.iastate.scribbleshare.User.User;
import edu.iastate.scribbleshare.User.UserController;
import edu.iastate.scribbleshare.User.UserRepository;


@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserController userController;

    HttpServletResponse resp = new MockHttpServletResponse();

    @BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

    @Test
    public void isFollowingTrueTest(){
        User user1 = mock(User.class);
        User user2 = new User("user2", "password", "user");
        HashSet<User> following = new HashSet<>();
        following.add(user2);
        when(userRepository.findById("user1")).thenReturn(Optional.of(user1));
        when(userRepository.findById("user2")).thenReturn(Optional.of(user2));
        when(user1.getUsername()).thenReturn("user1");
        when(user1.getFollowing()).thenReturn(following);
        String s = userController.isFollowing(resp, "user1", "user2");
        assertEquals("{following: true}", s);
    }

    @Test
    public void isFollowingFalseTest(){
        User user1 = mock(User.class);
        User user2 = new User("user2", "password", "user");
        HashSet<User> following = new HashSet<>();
        when(userRepository.findById("user1")).thenReturn(Optional.of(user1));
        when(userRepository.findById("user2")).thenReturn(Optional.of(user2));
        when(user1.getUsername()).thenReturn("user1");
        when(user1.getFollowing()).thenReturn(following);
        String s = userController.isFollowing(resp, "user1", "user2");
        assertEquals("{following: false}", s);
    }
}

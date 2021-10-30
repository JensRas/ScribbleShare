package edu.iastate.scribbleshare.Frame;

import javax.persistence.PostRemove;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.iastate.scribbleshare.Post.Post;
import edu.iastate.scribbleshare.Post.PostRepository;

@RestController
public class FrameController {
    @Autowired
    private FrameRepository frameRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping(path="/frames/{postId}")
    public Iterable<Frame> getFramesForPost(HttpServletResponse response, @PathVariable int postId){
        Post post = postRepository.findById(postId).get();
        return frameRepository.findByPost(post);
    }
}

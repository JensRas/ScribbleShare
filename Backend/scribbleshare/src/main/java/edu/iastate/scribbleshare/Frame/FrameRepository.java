package edu.iastate.scribbleshare.Frame;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.iastate.scribbleshare.Post.Post;

public interface FrameRepository extends CrudRepository<Frame, Integer>{
    List<Frame> findByPost(Post post);
}

package edu.iastate.scribbleshare.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends CrudRepository<Post, Integer>{
    
    //currently only selects all posts not created by the user
    @Query(value="select * from post p where p.username!=:username", nativeQuery = true)
    public Iterable<Post> getHomeScreenPosts(@Param("username") String userName);

}

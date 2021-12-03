package edu.iastate.scribbleshare.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.iastate.scribbleshare.User.User;

public interface PostRepository extends CrudRepository<Post, Integer>{
    
    /**
     * Query the repository to get recommended home screen posts
     * @param userName The username of the user whos home screen is to be populated
     * @return The list of posts to display on the specified user's home screen
     */
    // @Query(value="select * from post p where p.username!=:username order by id desc", nativeQuery = true) //TODO re-implement this
    @Query(value="select * from post order by id desc", nativeQuery = true)
    public Iterable<Post> getHomeScreenPosts(@Param("username") String userName);

    @Query(value="select * from post p where p.user_username=:user_username", nativeQuery = true)
    public Iterable<Post> getUserPosts(@Param("user_username")String username);

}

package edu.iastate.scribbleshare.Post;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends CrudRepository<Post, Integer>{
    
    /**
     * Query the repository to get recommended home screen posts
     * @param userName The username of the user whos home screen is to be populated
     * @return The list of posts to display on the specified user's home screen
     */
    @Query(value="select * from post order by id desc", nativeQuery = true)
    public Iterable<Post> getHomeScreenPosts(@Param("username") String userName);

    @Query(value="select * from post p where p.user_username=:user_username order by id desc", nativeQuery = true)
    public Iterable<Post> getUserPosts(@Param("user_username")String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO liked_posts (username, post_id) VALUES (:username, :post_id)", nativeQuery = true)
    public void addLikedPost(@Param("username") String username, @Param("post_id") int post_id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM liked_posts lp WHERE lp.username=:username AND lp.post_id=:post_id", nativeQuery = true)
    public void removeLikedPost(@Param("username") String username, @Param("post_id") int post_id);

    @Query(value = "SELECT count(*) FROM liked_posts lp where lp.post_id=:post_id", nativeQuery = true)
    public int getPostLikeCount(@Param("post_id") int post_id);

    @Query(value = "SELECT count(*) FROM liked_posts lp where lp.post_id=:post_id AND lp.username=:username", nativeQuery = true)
    public int getPostLikedByUser(@Param("post_id") int post_id, @Param("username") String username);

}

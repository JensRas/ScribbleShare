package edu.iastate.scribbleshare.User;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, String>{

    @Query(value = "select id from liked_posts lp JOIN post p ON p.id = lp.post_id where lp.username=:username", nativeQuery = true)
    public Set<Integer> getLikedPosts(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO liked_posts (username, post_id) VALUES (:username, :post_id)", nativeQuery = true)
    public void addLikedPost(@Param("username") String username, @Param("post_id") int post_id);

}

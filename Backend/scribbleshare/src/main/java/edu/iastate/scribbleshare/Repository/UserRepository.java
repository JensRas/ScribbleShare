package edu.iastate.scribbleshare.Repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.iastate.scribbleshare.Objects.User;

public interface UserRepository extends CrudRepository<User, String>{
    
    @Query(value="select * from user u where u.userName=:userName", nativeQuery = true)
    public Iterable<User> queryExample(@Param("userName") String userName);

    @Query(value="SELECT * FROM myDatabase.following INNER JOIN user ON user.username=following.follower_username WHERE followee_username=:username",
        nativeQuery = true)
    public Set<User> getUserFollowers(@Param("username") String username);
}

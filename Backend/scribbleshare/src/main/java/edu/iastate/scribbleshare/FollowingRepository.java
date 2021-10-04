package edu.iastate.scribbleshare;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FollowingRepository extends CrudRepository<Follower, String>{
    
    Follower findByUsername(String username);


   @Query(value="select * from follower u where u.username=:username", nativeQuery = true)
   public Iterable<Follower> queryUsers(@Param("username") String username);

   @Query(value="select * from follower u where u.following=:following", nativeQuery = true)
   public Iterable<Follower> queryFollowers(@Param("following") String following);

   @Query(value="select * from follower u where u.username=:username and u.following=:following", nativeQuery = true)
   public Follower queryFindByUsernameAndFollowing(@Param("username") String username, @Param("following") String following);
    
}
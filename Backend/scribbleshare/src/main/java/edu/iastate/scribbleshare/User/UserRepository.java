package edu.iastate.scribbleshare.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface UserRepository extends CrudRepository<User, String>{
    
    @Query(value="select * from user u where u.userName=:userName", nativeQuery = true)
    public Iterable<User> queryExample(@Param("userName") String userName);
    
}

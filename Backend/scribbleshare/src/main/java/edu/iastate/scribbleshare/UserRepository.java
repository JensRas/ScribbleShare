package edu.iastate.scribbleshare;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String>{
    
    @Query(value="select * from user u", nativeQuery = true)
    public Iterable<User> findBob();
}

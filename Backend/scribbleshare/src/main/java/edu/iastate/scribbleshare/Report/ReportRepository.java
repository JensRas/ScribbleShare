package edu.iastate.scribbleshare.Report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ReportRepository extends CrudRepository<Report, String>{
    
    @Query(value="select * from report u where u.username=:username", nativeQuery = true)
    public Iterable<Report> findAllByUsername(@Param("username") String  username);


}
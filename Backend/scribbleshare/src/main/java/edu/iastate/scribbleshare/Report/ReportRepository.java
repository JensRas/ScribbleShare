package edu.iastate.scribbleshare.Report;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface ReportRepository extends CrudRepository<Report, String>{
    
}
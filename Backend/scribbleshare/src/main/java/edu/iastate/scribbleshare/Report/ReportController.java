package edu.iastate.scribbleshare.Report;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import edu.iastate.scribbleshare.User.*;

@RestController
public class ReportController {
    
    @Autowired 
    ReportRepository repo;

    @Autowired
    UserRepository userRepo;

    @PutMapping(path = "/report/new")
    public void addNewReport(@RequestParam("username")String user, @RequestParam("userWhoReported")String userWhoReported, @RequestParam("comment") String comment, @RequestParam("reason") String reason){
        
        Optional<User> reporter = userRepo.findById(userWhoReported);
        Optional<User> reportee = userRepo.findById(user);
        if(!reporter.isPresent() || !reportee.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username doesn't exist");
      }
        
        Report report = new Report(user, userWhoReported, reason, comment);

        repo.save(report);
    }

    @GetMapping(path = "/report/getAllReports")
    public Iterable<Report> getAllReports(){
        return repo.findAll();
    }

    @GetMapping(path = "/report/{username}")
    public @ResponseBody Iterable<Report> getReportByUsername(@PathVariable String username){
        return repo.findAllByUsername(username);
    }

}


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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "ReportController", description = "REST API relating to Report Entity")
@RestController
public class ReportController {
    
    @Autowired 
    ReportRepository repo;

    @Autowired
    UserRepository userRepo;

    @ApiOperation(value = "Add New Report", response = Void.class, tags= "Report")
    @PutMapping(path = "/report/new")
    public void addNewReport(@RequestParam("username")User user, @RequestParam("userWhoReported")User userWhoReported, @RequestParam("comment") String comment, @RequestParam("reason") String reason){
        
        Optional<User> reporter = userRepo.findById(userWhoReported.getUsername());
        Optional<User> reportee = userRepo.findById(user.getUsername());
        if(!reporter.isPresent() || !reportee.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username doesn't exist");
      }
        
        Report report = new Report(user, userWhoReported, reason, comment);

        repo.save(report);
    }

    @ApiOperation(value = "Get All Reports", response = Iterable.class, tags= "Report")
    @GetMapping(path = "/report/getAllReports")
    public Iterable<Report> getAllReports(){
        return repo.findAll();
    }

    @ApiOperation(value = "Get Report By Username", response = Iterable.class, tags= "Report")
    @GetMapping(path = "/report/{username}")
    public @ResponseBody Iterable<Report> getReportByUsername(@PathVariable String username){
        return repo.findAllByUsername(username);
    }

}


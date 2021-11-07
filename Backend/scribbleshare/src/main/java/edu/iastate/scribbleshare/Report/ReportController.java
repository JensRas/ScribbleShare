package edu.iastate.scribbleshare.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ReportController {
    
    @Autowired 
    ReportRepository repo;

    @PutMapping(path = "/report/new")
    public void addNewReport(@RequestParam("username")String user, @RequestParam("comment") String comment, @RequestParam("reason") String reason){
        Report report = new Report(user, reason, comment);

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


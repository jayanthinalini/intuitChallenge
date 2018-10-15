package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.service.BidService;
import com.intuit.cg.backendtechassessment.service.ProjectService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.PROJECTS;

/**
 * ProjectController uses projectService, bidService for incoming requests
 * related to project and its bids
 *
 */
@Slf4j
@RestController
@RequestMapping(value = PROJECTS)
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private BidService bidService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Project> getProjects() {
        return projectService.getProjects();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Project getProject(@PathVariable("id") Project project) {
        return projectService.getProject(project);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @RequestMapping(value = "/{id}/bids", method = RequestMethod.GET)
    public List<Bid> getProjectBids(@PathVariable("id") Project project) {
        return projectService.getProjectBids(project);
    }

    @RequestMapping(value = "/{id}/bids", method = RequestMethod.POST)
    public Bid createProjectBid(@PathVariable("id") Project project,
            @RequestBody Bid bid) {
        return bidService.createProjectBid(project, bid);
    }

    @RequestMapping(value = "/{id}/bids/best", method = RequestMethod.GET)
    public Bid getProjectBidWithLowestAmount(@PathVariable("id") Long id) {
        return bidService.getProjectBidWithLowestAmount(id);
    }
}

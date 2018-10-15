package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
/**
 * Service methods involving Project
 * 
 * @author jayanthi
 *
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Iterable<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(Project project) {
        return projectRepository.findById(project.getId())
                .orElseThrow(NotFoundException::new);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // TODO move the below call to bidService?
    // TODO fix the exception to throw specific
    public List<Bid> getProjectBids(Project project) {
        return projectRepository.findById(project.getId())
                .orElseThrow(NotFoundException::new).getBids();
    }

}

package com.intuit.cg.backendtechassessment.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.testUtil.InstanceGenerator;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectService.class)
public class ProjectServiceTest {

    @Autowired
    ProjectService projectService;
    @MockBean
    ProjectRepository projectRepository;

    @Test
    public void testGetProjects() {
        Project projectOne = InstanceGenerator.getProject();
        Project projectTwo = InstanceGenerator.getProject();
        projectTwo.setId(2L);
        List<Project> projects = new ArrayList<Project>();
        projects.add(projectOne);
        projects.add(projectTwo);
        when(projectRepository.findAll()).thenReturn(projects);
        Iterable<Project> projectsReturned = projectService.getProjects();
        assertEquals(projects, projectsReturned);
    }

    @Test
    public void testGetProject() {
        Project newProject = InstanceGenerator.getProject();
        when(projectRepository.findById(newProject.getId()))
                .thenReturn(Optional.of(newProject));
        Project createdProject = projectService.getProject(newProject);
        assertEquals(createdProject, newProject);
    }

    @Test
    public void testCreateProject() {
        Project newProject = InstanceGenerator.getProject();
        when(projectRepository.save(newProject)).thenReturn(newProject);
        Project createdProject = projectService.createProject(newProject);
        assertEquals(createdProject.getId(), newProject.getId());
    }

    @Test
    public void testGetProjectBids() {
        Project projectWithBid = InstanceGenerator.getProject();
        projectWithBid.setBids(Arrays.asList(InstanceGenerator.getBid()));
        when(projectRepository.findById(projectWithBid.getId()))
                .thenReturn(Optional.of(projectWithBid));
        List<Bid> projectBids = projectService.getProjectBids(projectWithBid);
        assertEquals(projectWithBid.getBids(), projectBids);
    }

}

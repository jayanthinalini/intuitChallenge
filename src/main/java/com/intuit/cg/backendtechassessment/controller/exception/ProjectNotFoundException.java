package com.intuit.cg.backendtechassessment.controller.exception;
/**
 * ProjectNotFoundException extended from NotFoundException 
 * @author jayanthi
 *
 */
public class ProjectNotFoundException extends NotFoundException{
    
	 public ProjectNotFoundException() {
	        super("Project does not exist");
	    }
}

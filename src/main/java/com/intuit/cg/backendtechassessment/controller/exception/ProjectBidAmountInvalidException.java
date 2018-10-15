package com.intuit.cg.backendtechassessment.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * ProjectBidAmountInvalidException for invalid bid amount (-value)
 * @author jayanthi
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectBidAmountInvalidException extends RuntimeException {

    public ProjectBidAmountInvalidException() {
        super("Project bid amount is invalid.");
    }
}

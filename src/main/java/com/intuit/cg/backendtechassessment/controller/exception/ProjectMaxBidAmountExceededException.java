package com.intuit.cg.backendtechassessment.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * ProjectMaxBidAmountExceededException if exceeded maxBid
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectMaxBidAmountExceededException extends RuntimeException {

    public ProjectMaxBidAmountExceededException() {
        super("Project max bid amount has been exceeded.");
    }
}

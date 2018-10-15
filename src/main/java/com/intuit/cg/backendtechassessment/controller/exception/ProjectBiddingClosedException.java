package com.intuit.cg.backendtechassessment.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * ProjectBiddingClosedException if deadline passed
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectBiddingClosedException extends RuntimeException {

    public ProjectBiddingClosedException() {
        super("Bidding for this project is closed.");
    }
}

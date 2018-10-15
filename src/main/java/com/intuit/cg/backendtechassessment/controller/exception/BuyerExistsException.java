package com.intuit.cg.backendtechassessment.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * BuyerExistsException if exceeded maxBid
 * @author jayanthi
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BuyerExistsException extends RuntimeException {

    public BuyerExistsException() {
        super("Buyer already exists.");
    }
}

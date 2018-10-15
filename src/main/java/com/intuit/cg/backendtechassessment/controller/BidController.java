package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.service.BidService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.BIDS;
/**
 * BidController uses bidService for incoming requests related to bid
 *
 */
@Slf4j
@RestController
@RequestMapping(value = BIDS, produces = MediaType.APPLICATION_JSON_VALUE)
public class BidController {
    @Autowired
    private BidService bidService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Bid> getBids() {
        return bidService.getBids();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Bid getBid(@PathVariable("id") Bid bid) {
        return bidService.getBid(bid);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public Bid updateBid(@PathVariable("id") Bid bid, @RequestBody Bid newBid) {
        return bidService.updateBidAmount(bid, newBid);
    }
}

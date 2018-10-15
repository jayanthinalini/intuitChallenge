package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.service.BuyerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.BUYERS;

/**
 * BuyerController uses buyerService for incoming requests related to buyer
 *
 */
@Slf4j
@RestController
@RequestMapping(value = BUYERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Buyer> getBuyers() {
        return buyerService.getBuyers();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Buyer createBuyer(@RequestBody Buyer buyer) {
        return buyerService.createBuyer(buyer);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Buyer getBuyer(@PathVariable("id") Buyer buyer) {
        return buyerService.getBuyer(buyer);
    }

    @RequestMapping(value = "/{id}/bids", method = RequestMethod.GET)
    public List<Bid> getBuyerBids(@NotNull @PathVariable("id") Buyer buyer) {
        return buyerService.getBuyerBids(buyer);
    }

}

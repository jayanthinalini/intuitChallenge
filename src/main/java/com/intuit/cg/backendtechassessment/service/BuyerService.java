package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.repository.BuyerRepository;
/**
 * Service methods involving Buyer
 * 
 * @author jayanthi
 *
 */
@Service
public class BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    public Iterable<Buyer> getBuyers() {
        return buyerRepository.findAll();
    }

    public Buyer createBuyer(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    // TODO fix the exception to throw specific
    public Buyer getBuyer(Buyer buyer) {
        return buyerRepository.findById(buyer.getId())
                .orElseThrow(NotFoundException::new);
    }

    // TODO fix the exception to throw specific
    public List<Bid> getBuyerBids(Buyer buyer) {
        return buyerRepository.findById(buyer.getId())
                .orElseThrow(NotFoundException::new).getBids();
    }

}

package com.intuit.cg.backendtechassessment.repository;

import com.intuit.cg.backendtechassessment.entity.Bid;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BidRepository extends CrudRepository<Bid, Long> {
    Bid findTopByProjectIdOrderByAmountAsc(Long projectId);
    // find bids that has autobid enabled/disabled for a particular project
    List<Bid> findByProjectIdAndAutoBid(Long projectId, Boolean autoBid);
}

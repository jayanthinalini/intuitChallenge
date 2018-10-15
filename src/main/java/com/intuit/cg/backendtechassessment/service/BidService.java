package com.intuit.cg.backendtechassessment.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectBidAmountInvalidException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectBiddingClosedException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectMaxBidAmountExceededException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectNotFoundException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.repository.BidRepository;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.validator.BidValidator;

import java.math.BigDecimal;
import java.util.List;
/**
 * Service methods involving bids
 * 
 * @author jayanthi
 *
 */
@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;
    @Autowired
    ProjectRepository projectRepository;

    public Iterable<Bid> getBids() {
        return bidRepository.findAll();
    }

    // TODO fix the exception to throw specific
    /**
     * Return the Bid object
     * 
     * @param bid
     * @return
     */
    public Bid getBid(Bid bid) {
        return bidRepository.findById(bid.getId())
                .orElseThrow(NotFoundException::new);
    }

    /**
     * update the bid amount given the newBid amount
     * 
     * @param bid
     * @param newBid
     * @return
     */
    public Bid updateBidAmount(Bid bid, Bid newBid) {
        // validations
        this.validateProjectBid(bid.getProject(), newBid);
        bid.setAmount(newBid.getAmount());
        return bidRepository.save(bid);
    }

    /**
     * create a new bid for the project. takes care of updating autobids if any
     * exists for this project
     * 
     * @param project
     * @param bid
     * @return
     */
    public Bid createProjectBid(Project project, Bid bid) {
        // validations
        this.validateProjectBid(project, bid);
        bid.setProject(project);
        // check for autoBids
        List<Bid> autoBids = this.getProjectBidsWithAutoBid_On(project.getId());
        // if there are autobids already
        if (autoBids.size() > 0) {
            autoBids = this.updateAllAutoBids(autoBids, bid);
            bidRepository.saveAll(autoBids);
        }
        // if this is the first autobid
        else if (bid.getAutoBid()) {
            // there is a minBid
            Bid minBid = bidRepository.findTopByProjectIdOrderByAmountAsc(
                    bid.getProject().getId());
            if (bid.getMinAmount().compareTo(minBid.getAmount()) < 1)
                bid = this.updateAutoBidAmount(bid, minBid.getAmount());
        }
        return bidRepository.save(bid);
    }

    /**
     * validated a projectBid , used before creation/updation of a projectBid
     * 
     * @param project
     * @param bid
     */
    public void validateProjectBid(Project project, Bid bid) {
        Optional<Project> oProject = projectRepository
                .findById(project.getId());
        if (!oProject.isPresent())
            throw new ProjectNotFoundException();
        if (!BidValidator.validBidAmount(bid.getAmount()))
            throw new ProjectBidAmountInvalidException();
        if (!BidValidator.bidAmountNotExceeded(project.getMaxBidAmount(),
                bid.getAmount()))
            throw new ProjectMaxBidAmountExceededException();
        if (project.getBidDeadline() != null
                && BidValidator.projectDeadlinePassed(project.getBidDeadline()))
            throw new ProjectBiddingClosedException();
    }

    /**
     * retruns the bid that has the lowest amount that belongs to the
     * project(id) to the passed project
     * 
     * @param id
     *            of project
     * @return bid with lowest amount
     */
    public Bid getProjectBidWithLowestAmount(Long id) {
        return Optional
                .ofNullable(
                        bidRepository.findTopByProjectIdOrderByAmountAsc(id))
                .orElseThrow(NotFoundException::new);
    }

    /**
     * return all bids that has autobid enabled under this project
     * 
     * @param id
     * @return
     */
    public List<Bid> getProjectBidsWithAutoBid_On(Long id) {
        return bidRepository.findByProjectIdAndAutoBid(id, true);
    }

    /**
     * updateAllBids that has auto-bid set to true based on minAmount, decrement
     * and currently submitted bid's amount
     * 
     * @param autoBids,bid
     */
    public List<Bid> updateAllAutoBids(List<Bid> autoBids, Bid bid) {
        Stream<Bid> filteredBids = autoBids.stream()
                .filter(x -> (x.getMinAmount().compareTo(bid.getAmount()) < 1))
                .map(x -> this.updateAutoBidAmount(x, bid.getAmount()));
        List<Bid> updatedBids = filteredBids.collect(Collectors.toList());
        return updatedBids;
    }

    /**
     * update the autobid's bidAmount based on new bid's amount
     * 
     * @param autoBid
     * @param newBidAmount
     * @return
     */
    public Bid updateAutoBidAmount(Bid autoBid, BigDecimal newBidAmount) {
        BigDecimal updatedAutoBidAmount = newBidAmount
                .subtract(autoBid.getDecrement());
        autoBid.setAmount(
                ((autoBid.getMinAmount().compareTo(updatedAutoBidAmount) < 1)
                        ? updatedAutoBidAmount
                        : autoBid.getMinAmount()));
        return autoBid;
    }

}

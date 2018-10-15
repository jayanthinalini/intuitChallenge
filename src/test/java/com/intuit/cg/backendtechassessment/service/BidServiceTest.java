package com.intuit.cg.backendtechassessment.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.cg.backendtechassessment.controller.exception.ProjectBidAmountInvalidException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectBiddingClosedException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectMaxBidAmountExceededException;
import com.intuit.cg.backendtechassessment.controller.exception.ProjectNotFoundException;
import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.repository.BidRepository;
import com.intuit.cg.backendtechassessment.repository.ProjectRepository;
import com.intuit.cg.backendtechassessment.testUtil.InstanceGenerator;

@RunWith(SpringRunner.class)
@WebMvcTest(BidService.class)
public class BidServiceTest {

    @Autowired
    BidService bidService;
    @MockBean
    BidRepository bidRepository;
    @MockBean
    ProjectRepository projectRepository;

    @Test
    public void testGetBids() {
        Bid bidOne = InstanceGenerator.getBid();
        Bid bidTwo = InstanceGenerator.getBid();
        bidTwo.setId(2L);
        List<Bid> bids = new ArrayList<Bid>();
        bids.add(bidOne);
        bids.add(bidTwo);
        when(bidRepository.findAll()).thenReturn(bids);
        Iterable<Bid> bidsReturned = bidService.getBids();
        assertEquals(bids, bidsReturned);
    }

    @Test
    public void testGetBid() {
        Bid newBid = InstanceGenerator.getBid();
        when(bidRepository.findById(newBid.getId()))
                .thenReturn(Optional.of(newBid));
        Bid createdBid = bidService.getBid(newBid);
        assertEquals(createdBid, newBid);
    }

    @Test
    public void testUpdateBid() {
        Bid bid = InstanceGenerator.getBid();
        Project project = InstanceGenerator.getProject();
        bid.setProject(project);
        Bid updatedBid = InstanceGenerator.getBid();
        updatedBid.setAmount(BigDecimal.valueOf(4000));
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));
        when(bidRepository.save(bid)).thenReturn(bid);
        Bid returnedBid = bidService.updateBidAmount(bid, updatedBid);
        assertEquals(updatedBid.getAmount(), returnedBid.getAmount());
    }

    @Test
    public void testCreateProjectBid() {
        Bid newBid = InstanceGenerator.getBid();
        Project project = InstanceGenerator.getProject();
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));
        when(bidRepository.save(newBid)).thenReturn(newBid);
        Bid createdBid = bidService.createProjectBid(project, newBid);
        assertEquals(createdBid.getProject(), project);
    }

    @Test
    public void testCreateProjectBid_FirstAutoBid() {
        Bid newBid = InstanceGenerator.getBid();
        Project project = InstanceGenerator.getProject();
        newBid.setAmount(BigDecimal.valueOf(5000));
        newBid.setAutoBid(true);
        newBid.setDecrement(BigDecimal.valueOf(200));
        // set minAmount less than current min bid
        newBid.setMinAmount(BigDecimal.valueOf(1000));
        newBid.setProject(project);
        // current min bid
        Bid someBid = InstanceGenerator.getBid();
        someBid.setAmount(BigDecimal.valueOf(2000));
        someBid.setAutoBid(false);
        someBid.setProject(project);

        // when(bidRepository.findByProjectIdAndAutoBid(project.getId(),
        // true)).then
        // bidTwo is stubbed already to contain lowest amount
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));
        when(bidRepository.findTopByProjectIdOrderByAmountAsc(project.getId()))
                .thenReturn(someBid);
        when(bidRepository.save(newBid)).thenReturn(newBid);
        Bid createdBid = bidService.createProjectBid(project, newBid);
        assertEquals(createdBid.getProject(), project);
        assertEquals(createdBid.getAmount(),
                someBid.getAmount().subtract(newBid.getDecrement()));
    }

    @Test
    public void testCreateProjectBid_WithExistingAutoBid() {
        // various autobid cases
        Project project = InstanceGenerator.getProject();
        List<Bid> bids = this.getStubbedBidCases(project, true);
        Bid bidOne = bids.get(0);
        Bid bidTwo = bids.get(1);
        Bid bidThree = bids.get(2);
        // new bid comes in
        Bid newBid = InstanceGenerator.getBid();
        newBid.setAmount(BigDecimal.valueOf(1099));
        newBid.setProject(project);

        // mocks for repo calls
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));
        when(bidRepository.findByProjectIdAndAutoBid(project.getId(), true))
                .thenReturn(bids);
        when(bidRepository.saveAll(bids)).thenReturn(bids);
        when(bidRepository.save(newBid)).thenReturn(newBid);

        Bid createdBid = bidService.createProjectBid(project, newBid);
        // assert project update on new bid
        assertEquals(createdBid.getProject(), project);
        // assert autobid amount changes
        for (Bid bid : bids) {
            if (bid.getId() == 1) { // follows Test caseB of updateAutoBidAmount
                assertEquals(bid.getAmount(), bidOne.getMinAmount());
            }
            if (bid.getId() == 2) { // follows Test caseA of updateAutoBidAmount
                assertEquals(bid.getAmount(),
                        newBid.getAmount().subtract(bidTwo.getDecrement()));
            }
            if (bid.getId() == 3) { // above minAmount, no change
                assertEquals(bid.getAmount(), bidThree.getAmount());
            }
        }

    }

    @Test
    public void testGetProjectBidWithLowestAmount() {
        Bid bidOne = InstanceGenerator.getBid();
        when(bidRepository.findTopByProjectIdOrderByAmountAsc(1L))
                .thenReturn(bidOne);
        Bid returnedBid = bidService.getProjectBidWithLowestAmount(1L);
        assertEquals(returnedBid, bidOne);
    }

    @Test
    public void testUpdateAutoBidAmount_CaseA() {
        Project project = InstanceGenerator.getProject();
        Bid bidOne = InstanceGenerator.getBid();
        bidOne.setAutoBid(true);
        bidOne.setProject(project);
        bidOne.setMinAmount(BigDecimal.valueOf(1000));
        bidOne.setDecrement(BigDecimal.valueOf(100));

        Bid nextBid = InstanceGenerator.getBid();
        nextBid.setAmount(BigDecimal.valueOf(2000));
        // in this case 1000<2000, normal scenario, should return (2000-100),
        // decremented value as new Amount
        Bid returnedBid = bidService.updateAutoBidAmount(bidOne,
                nextBid.getAmount());
        assertEquals(returnedBid.getAmount(),
                nextBid.getAmount().subtract(bidOne.getDecrement()));
    }

    @Test
    public void testUpdateAutoBidAmount_CaseB() {
        Project project = InstanceGenerator.getProject();
        Bid bidOne = InstanceGenerator.getBid();
        bidOne.setAutoBid(true);
        bidOne.setProject(project);
        bidOne.setMinAmount(BigDecimal.valueOf(1000));
        bidOne.setDecrement(BigDecimal.valueOf(100));

        Bid nextBid = InstanceGenerator.getBid();
        nextBid.setAmount(BigDecimal.valueOf(1099));
        // in this case 1099>1000 , but decrementing 100 will be 999 (<1000, ie
        // minAmount)
        // the updated amount should be minAmount
        Bid returnedBid = bidService.updateAutoBidAmount(bidOne,
                nextBid.getAmount());
        assertEquals(returnedBid.getAmount(), bidOne.getMinAmount());
    }

    @Test
    public void testUpdateAllAutoBids() {
        Project project = InstanceGenerator.getProject();
        // various autobid cases
        List<Bid> bids = this.getStubbedBidCases(project, true);
        Bid bidOne = bids.get(0);
        Bid bidTwo = bids.get(1);
        Bid bidThree = bids.get(2);

        Bid nextBid = InstanceGenerator.getBid();
        nextBid.setAmount(BigDecimal.valueOf(1099));
        nextBid.setProject(project);

        List<Bid> returnedBids = bidService.updateAllAutoBids(bids, nextBid);

        for (Bid bid : returnedBids) {
            if (bid.getId() == 1) { // follows Test caseB of updateAutoBidAmount
                assertEquals(bid.getAmount(), bidOne.getMinAmount());
            }
            if (bid.getId() == 2) { // follows Test caseA of updateAutoBidAmount
                assertEquals(bid.getAmount(),
                        nextBid.getAmount().subtract(bidTwo.getDecrement()));
            }
            if (bid.getId() == 3) { // above minAmount, no change
                assertEquals(bid.getAmount(), bidThree.getAmount());
            }
        }

    }

    @Test(expected = ProjectNotFoundException.class)
    public void testValidateProjectBid_NoProject() {
        Project project = InstanceGenerator.getProject();
        Bid bidOne = InstanceGenerator.getBid();
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.empty());
        bidService.validateProjectBid(project, bidOne);
    }

    @Test(expected = ProjectBidAmountInvalidException.class)
    public void testValidateProjectBid_InvalidBidAmount() {
        Project project = InstanceGenerator.getProject();
        Bid bidOne = InstanceGenerator.getBid();
        bidOne.setAmount(BigDecimal.valueOf(-1));
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));
        bidService.validateProjectBid(project, bidOne);
    }

    @Test(expected = ProjectMaxBidAmountExceededException.class)
    public void testValidateProjectBid_MaxBidAmountExceeded() {
        Project project = InstanceGenerator.getProject();
        Bid bidOne = InstanceGenerator.getBid();
        bidOne.setAmount(project.getMaxBidAmount().add(BigDecimal.valueOf(10)));
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));
        bidService.validateProjectBid(project, bidOne);
    }

    @Test(expected = ProjectBiddingClosedException.class)
    public void testValidateProjectBid_BiddingClosed() {
        Project project = InstanceGenerator.getProject();
        project.setBidDeadline(project.getBidDeadline().minusDays(10));
        Bid bidOne = InstanceGenerator.getBid();
        when(projectRepository.findById(project.getId()))
                .thenReturn(Optional.of(project));
        bidService.validateProjectBid(project, bidOne);
    }

    public List<Bid> getStubbedBidCases(Project project, Boolean autoBidFlag) {
        // already set amount to 5000
        Bid bidOne = InstanceGenerator.getBid();
        bidOne.setAutoBid(autoBidFlag);
        bidOne.setProject(project);
        bidOne.setDecrement(BigDecimal.valueOf(100));
        bidOne.setMinAmount(BigDecimal.valueOf(1000));

        Bid bidTwo = InstanceGenerator.getBid();
        bidTwo.setId(2L);
        bidTwo.setAutoBid(autoBidFlag);
        bidTwo.setAmount(BigDecimal.valueOf(2000));
        bidTwo.setProject(project);
        bidTwo.setDecrement(BigDecimal.valueOf(200));
        bidTwo.setMinAmount(BigDecimal.valueOf(500));

        Bid bidThree = InstanceGenerator.getBid();
        bidThree.setId(3L);
        bidThree.setAutoBid(autoBidFlag);
        bidThree.setProject(project);
        bidThree.setDecrement(BigDecimal.valueOf(200));
        bidThree.setMinAmount(BigDecimal.valueOf(3000));

        List<Bid> bids = new ArrayList<Bid>();
        bids.add(bidOne);
        bids.add(bidTwo);
        bids.add(bidThree);
        return bids;

    }

}

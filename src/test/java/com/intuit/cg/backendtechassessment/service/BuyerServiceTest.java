package com.intuit.cg.backendtechassessment.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.repository.BuyerRepository;
import com.intuit.cg.backendtechassessment.testUtil.InstanceGenerator;

@RunWith(SpringRunner.class)
@WebMvcTest(BuyerService.class)
public class BuyerServiceTest {

    @Autowired
    BuyerService buyerService;
    @MockBean
    BuyerRepository buyerRepository;

    @Test
    public void testGetBuyers() {
        Buyer buyerOne = InstanceGenerator.getBuyer();
        Buyer buyerTwo = InstanceGenerator.getBuyer();
        buyerTwo.setId(2L);
        List<Buyer> buyers = new ArrayList<Buyer>();
        buyers.add(buyerOne);
        buyers.add(buyerTwo);
        when(buyerRepository.findAll()).thenReturn(buyers);
        Iterable<Buyer> buyersReturned = buyerService.getBuyers();
        assertEquals(buyers, buyersReturned);
    }

    @Test
    public void testCreateBuyer() {
        Buyer newBuyer = InstanceGenerator.getBuyer();
        when(buyerRepository.findById(newBuyer.getId()))
                .thenReturn(Optional.of(newBuyer));
        Buyer createdBuyer = buyerService.getBuyer(newBuyer);
        assertEquals(createdBuyer, newBuyer);
    }

    @Test
    public void testGetBuyer() {
        Buyer newBuyer = InstanceGenerator.getBuyer();
        when(buyerRepository.save(newBuyer)).thenReturn(newBuyer);
        Buyer createdBuyer = buyerService.createBuyer(newBuyer);
        assertEquals(createdBuyer.getId(), newBuyer.getId());
    }

    @Test
    public void testGetBuyerBids() {
        Buyer buyerWithBid = InstanceGenerator.getBuyer();
        buyerWithBid.setBids(Arrays.asList(InstanceGenerator.getBid()));
        when(buyerRepository.findById(buyerWithBid.getId()))
                .thenReturn(Optional.of(buyerWithBid));
        List<Bid> buyerBids = buyerService.getBuyerBids(buyerWithBid);
        assertEquals(buyerBids, buyerWithBid.getBids());
    }

}

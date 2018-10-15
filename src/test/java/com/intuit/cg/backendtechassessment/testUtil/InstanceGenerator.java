package com.intuit.cg.backendtechassessment.testUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.intuit.cg.backendtechassessment.entity.Bid;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.Seller;

public class InstanceGenerator {

    public static Buyer getBuyer() {
        Buyer newBuyer = new Buyer();
        newBuyer.setId(1L);
        newBuyer.setFirstName("A Buyer name");
        newBuyer.setLastName("my last name");
        newBuyer.setPhoneNumber("234-345-7777");
        newBuyer.setEmail("someBuyerEmail@email.com");

        return newBuyer;

    }

    public static Seller getSeller() {
        Seller newSeller = new Seller();
        newSeller.setId(1L);
        newSeller.setFirstName("A Seller name");
        newSeller.setLastName("my last name");
        newSeller.setPhoneNumber("234-345-7777");
        newSeller.setEmail("someSellerEmail@email.com");

        return newSeller;
    }

    public static Project getProject() {
        Project newProject = new Project();
        newProject.setId(1L);
        newProject.setSeller(getSeller());
        newProject.setName("Project Jay");
        newProject.setDescription("My own description");
        newProject.setMaxBidAmount(BigDecimal.valueOf(6000));
        newProject.setBidDeadline(LocalDateTime.now().plusDays(5));
        return newProject;
    }

    public static Bid getBid() {
        Bid newBid = new Bid();
        newBid.setId(1L);
        newBid.setAutoBid(false);
        newBid.setBuyer(getBuyer());
        newBid.setAmount(BigDecimal.valueOf(5000));
        newBid.setCreatedDate(LocalDateTime.now());
        return newBid;
    }

    public static Bid getProjectBid() {
        Bid projectBid = getBid();
        projectBid.setProject(getProject());
        return projectBid;
    }

}

package com.intuit.cg.backendtechassessment.service;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.Seller;
import com.intuit.cg.backendtechassessment.repository.SellerRepository;
import com.intuit.cg.backendtechassessment.testUtil.InstanceGenerator;

@RunWith(SpringRunner.class)
@WebMvcTest(SellerService.class)
public class SellerServiceTest {

    @Autowired
    SellerService sellerService;
    @MockBean
    SellerRepository sellerRepository;

    @Test
    public void testGetSellers() {
        Seller sellerOne = InstanceGenerator.getSeller();
        Seller sellerTwo = InstanceGenerator.getSeller();
        sellerTwo.setId(2L);
        List<Seller> sellers = new ArrayList<Seller>();
        sellers.add(sellerOne);
        sellers.add(sellerTwo);
        when(sellerRepository.findAll()).thenReturn(sellers);
        Iterable<Seller> sellersReturned = sellerService.getSellers();
        assertEquals(sellers, sellersReturned);
    }

    @Test
    public void testGetSeller() {
        Seller newSeller = InstanceGenerator.getSeller();
        when(sellerRepository.findById(newSeller.getId()))
                .thenReturn(Optional.of(newSeller));
        Seller createdSeller = sellerService.getSeller(newSeller);
        assertEquals(createdSeller, newSeller);
    }

    @Test
    public void testCreateSeller() {
        Seller newSeller = InstanceGenerator.getSeller();
        when(sellerRepository.save(newSeller)).thenReturn(newSeller);
        Seller createdSeller = sellerService.createSeller(newSeller);
        assertEquals(createdSeller.getId(), newSeller.getId());
    }

    @Test
    public void testGetSellerProjects() {
        Seller sellerWithProject = InstanceGenerator.getSeller();
        sellerWithProject
                .setProjects(Arrays.asList(InstanceGenerator.getProject()));
        when(sellerRepository.findById(sellerWithProject.getId()))
                .thenReturn(Optional.of(sellerWithProject));
        List<Project> sellerProjects = sellerService
                .getSellerProjects(sellerWithProject);
        assertEquals(sellerWithProject.getProjects(), sellerProjects);
    }

}

package com.intuit.cg.backendtechassessment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intuit.cg.backendtechassessment.controller.exception.NotFoundException;
import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.Seller;
import com.intuit.cg.backendtechassessment.repository.SellerRepository;
/**
 * Service methods involving Seller
 * 
 * @author jayanthi
 *
 */
@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    public Iterable<Seller> getSellers() {
        return sellerRepository.findAll();
    }

    public Seller getSeller(Seller seller) {
        return sellerRepository.findById(seller.getId())
                .orElseThrow(NotFoundException::new);
    }

    public Seller createSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    // TODO fix the exception to throw specific
    public List<Project> getSellerProjects(Seller seller) {
        return sellerRepository.findById(seller.getId())
                .orElseThrow(NotFoundException::new).getProjects();
    }
}

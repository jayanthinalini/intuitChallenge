package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.entity.Project;
import com.intuit.cg.backendtechassessment.entity.Seller;
import com.intuit.cg.backendtechassessment.service.SellerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.intuit.cg.backendtechassessment.controller.requestmappings.RequestMappings.SELLERS;

/**
 * SellerController uses sellerService for incoming requests related to service
 *
 */
@Slf4j
@RestController
@RequestMapping(value = SELLERS, produces = MediaType.APPLICATION_JSON_VALUE)
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Seller> getSellers() {
        return sellerService.getSellers();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Seller getSeller(@PathVariable("id") Seller seller) {
        return sellerService.getSeller(seller);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Seller createSeller(@RequestBody Seller seller) {
        return sellerService.createSeller(seller);
    }

    @RequestMapping(value = "/{id}/projects", method = RequestMethod.GET)
    public List<Project> getSellerProjects(@PathVariable("id") Seller seller) {
        return sellerService.getSellerProjects(seller);
    }
}

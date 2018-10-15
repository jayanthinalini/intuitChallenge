package com.intuit.cg.backendtechassessment.controllertest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.intuit.cg.backendtechassessment.controller.SellerController;
import com.intuit.cg.backendtechassessment.repository.SellerRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(SellerController.class)
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerRepository sellerRepository;

}

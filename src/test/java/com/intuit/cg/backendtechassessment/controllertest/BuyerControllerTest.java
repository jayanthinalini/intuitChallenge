package com.intuit.cg.backendtechassessment.controllertest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import com.intuit.cg.backendtechassessment.controller.BuyerController;
import com.intuit.cg.backendtechassessment.service.BuyerService;

@RunWith(SpringRunner.class)
@WebMvcTest(BuyerController.class)
public class BuyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerService buyerService;

}

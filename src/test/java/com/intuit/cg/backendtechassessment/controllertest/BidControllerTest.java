package com.intuit.cg.backendtechassessment.controllertest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.intuit.cg.backendtechassessment.controller.BidController;
import com.intuit.cg.backendtechassessment.service.BidService;

@RunWith(SpringRunner.class)
@WebMvcTest(BidController.class)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidService bidService;

}

package com.intuit.cg.backendtechassessment.controllertest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.intuit.cg.backendtechassessment.controller.BuyerController;
import com.intuit.cg.backendtechassessment.entity.Buyer;
import com.intuit.cg.backendtechassessment.service.BuyerService;
import com.intuit.cg.backendtechassessment.testUtil.InstanceGenerator;
import com.intuit.cg.backendtechassessment.testUtil.JsonUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(BuyerController.class)
public class BuyerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuyerService buyerService;

}

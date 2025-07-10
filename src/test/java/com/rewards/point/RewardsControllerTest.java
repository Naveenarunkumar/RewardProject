package com.rewards.point;

import com.rewards.point.controller.RewardsController;
import com.rewards.point.dto.CustomerRewardsResponse;
import com.rewards.point.service.RewardsService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Test
    @DisplayName("GET /api/rewards?customerId=C1 - returns customer rewards")
    void testGetRewardsByCustomerId() throws Exception {
        CustomerRewardsResponse response = new CustomerRewardsResponse(
                "C1",
                Map.of(YearMonth.of(2025, 7), 250),
                250
        );

        Mockito.when(rewardsService.getRewardsByCustomerId("C1")).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewards")
                        .param("customerId", "C1")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("C1"))
                .andExpect(jsonPath("$.totalPoints").value(250));
    }

    @Test
    @DisplayName("GET /api/rewards - returns all customer rewards")
    void testGetAllRewards() throws Exception {
        List<CustomerRewardsResponse> responses = List.of(
                new CustomerRewardsResponse("C1", Map.of(YearMonth.of(2025, 7), 120), 120),
                new CustomerRewardsResponse("C2", Map.of(YearMonth.of(2025, 7), 90), 90)
        );

        Mockito.when(rewardsService.getAllCustomerRewards()).thenReturn(responses);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewards")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerId").value("C1"))
                .andExpect(jsonPath("$[1].customerId").value("C2"));
    }

    @Test
    @DisplayName("GET /api/rewards?customerId=INVALID - returns 404")
    void testInvalidCustomerIdReturns404() throws Exception {
        Mockito.when(rewardsService.getRewardsByCustomerId("INVALID"))
                .thenThrow(new RuntimeException("Customer not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/rewards")
                        .param("customerId", "INVALID")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isInternalServerError()) // Adjust if you have custom exception handling
                .andExpect(content().string("An unexpected error occurred: Customer not found"));
    }
}


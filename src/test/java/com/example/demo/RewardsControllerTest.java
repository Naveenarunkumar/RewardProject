package com.example.demo;

import com.example.demo.*;
import com.example.demo.dto.CustomerRewardsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for RewardsController with multiple customers.
 */
@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    private CustomerRewardsResponse c1Response;
    private CustomerRewardsResponse c2Response;

    @BeforeEach
    void setup() {
        c1Response = new CustomerRewardsResponse(
                "C1",
                Map.of(YearMonth.of(2025, 6), 115, YearMonth.of(2025, 7), 250),
                365
        );

        c2Response = new CustomerRewardsResponse(
                "C2",
                Map.of(YearMonth.of(2025, 6), 45, YearMonth.of(2025, 7), 110),
                155
        );
    }

    @Test
    void testGetRewardsByCustomerId() throws Exception {
        when(rewardsService.getRewardsByCustomerId("C1")).thenReturn(c1Response);

        mockMvc.perform(get("/api/rewards")
                        .param("customerId", "C1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("C1"))
                .andExpect(jsonPath("$.totalPoints").value(365))
                .andExpect(jsonPath("$.monthlyPoints['2025-06']").value(115));
    }

    @Test
    void testGetAllRewards() throws Exception {
        when(rewardsService.getAllCustomerRewards()).thenReturn(List.of(c1Response, c2Response));

        mockMvc.perform(get("/api/rewards")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value("C1"))
                .andExpect(jsonPath("$[1].customerId").value("C2"))
                .andExpect(jsonPath("$[1].totalPoints").value(155));
    }
}
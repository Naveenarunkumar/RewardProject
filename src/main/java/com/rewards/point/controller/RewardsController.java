package com.rewards.point.controller;

import com.rewards.point.dto.CustomerRewardsResponse;
import com.rewards.point.service.RewardsService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing reward point calculations for customers.
 * <p>
 * Provides endpoints to retrieve reward summaries for individual customers
 * or all customers in the system.
 * </p>
 */

@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private final RewardsService rewardsService;
    
    /**
     * Constructs a new {@code RewardsController} with the specified {@link RewardsService}.
     *
     * @param rewardsService the service used to calculate and retrieve reward data
     */


    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * GET endpoint to retrieve reward points for a specific customer.
     *
     * @param customerId the ID of the customer
     * @parm used for Only matches if the specified query parameter is present (or has a specific value)
     * @return reward points summary for the customer
     */
    @GetMapping(params = "customerId")
    public ResponseEntity<CustomerRewardsResponse> getRewardsByCustomerId(
            @RequestParam String customerId) {
        CustomerRewardsResponse response = rewardsService.getRewardsByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }
    

    /**
     * GET endpoint to retrieve reward points for all customers.
     *
     * @return list of reward summaries for all customers
     */
    @GetMapping("/fetchAll")
    public ResponseEntity<List<CustomerRewardsResponse>> getAllRewards() {
        List<CustomerRewardsResponse> responses = rewardsService.getAllCustomerRewards();
        return ResponseEntity.ok(responses);
    }
}


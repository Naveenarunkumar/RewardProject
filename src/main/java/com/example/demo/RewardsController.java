//package com.example.demo;
//
//import com.example.demo.dto.CustomerRewardsResponse;
//
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * REST controller for handling reward point calculations.
// */
//@RestController
//@RequestMapping("/api/rewards")
//public class RewardsController {
//
//    private final RewardsService rewardsService;
//
//    public RewardsController(RewardsService rewardsService) {
//        this.rewardsService = rewardsService;
//    }
//    
//
//    /**
//     * GET endpoint to retrieve reward points for a specific customer.
//     *
//     * @param customerId the ID of the customer
//     * @return reward points summary for the customer
//     */
//    @GetMapping
//    public ResponseEntity<CustomerRewardsResponse> getRewardsByCustomerId(
//            @RequestParam String customerId) {
//        return ResponseEntity.ok(rewardsService.getRewardsByCustomerId(customerId));
//    }
//}


package com.example.demo;

import com.example.demo.dto.CustomerRewardsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling reward point calculations.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * GET endpoint to retrieve reward points for a specific customer.
     *
     * @param customerId the ID of the customer
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
    @GetMapping
    public ResponseEntity<List<CustomerRewardsResponse>> getAllRewards() {
        List<CustomerRewardsResponse> responses = rewardsService.getAllCustomerRewards();
        return ResponseEntity.ok(responses);
    }
}
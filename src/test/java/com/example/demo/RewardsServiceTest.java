package com.example.demo;

import com.example.demo.dto.CustomerRewardsResponse;
import com.example.demo.exception.InvalidTransactionException;
import com.example.demo.model.Transaction;
import com.example.demo.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RewardsService with multiple customers and transactions.
 */
public class RewardsServiceTest {

    private final RewardsService rewardsService = new RewardsService() {
        protected List<Transaction> getMockedTransactions() {
            return List.of(
                new Transaction("C1", 120, LocalDate.of(2025, 6, 1)),   // 90 pts
                new Transaction("C1", 75, LocalDate.of(2025, 6, 15)),   // 25 pts
                new Transaction("C1", 200, LocalDate.of(2025, 7, 10)),  // 250 pts
                new Transaction("C2", 95, LocalDate.of(2025, 6, 5)),    // 45 pts
                new Transaction("C2", 130, LocalDate.of(2025, 7, 20)),  // 110 pts
                new Transaction("C3", 45, LocalDate.of(2025, 6, 25)),   // 0 pts
                new Transaction("C3", 105, LocalDate.of(2025, 7, 5))    // 60 pts
            );
        }
    };

    @Test
    void testGetRewardsByCustomerId_C1() {
        CustomerRewardsResponse response = rewardsService.getRewardsByCustomerId("C1");

        assertEquals("C1", response.getCustomerId());
        assertEquals(3, response.getMonthlyPoints().size());
        assertEquals(90 + 25, response.getMonthlyPoints().get(YearMonth.of(2025, 6)));
        assertEquals(250, response.getMonthlyPoints().get(YearMonth.of(2025, 7)));
        assertEquals(365, response.getTotalPoints());
    }

    @Test
    void testGetRewardsByCustomerId_C2() {
        CustomerRewardsResponse response = rewardsService.getRewardsByCustomerId("C2");

        assertEquals("C2", response.getCustomerId());
        assertEquals(2, response.getMonthlyPoints().size());
        assertEquals(45, response.getMonthlyPoints().get(YearMonth.of(2025, 6)));
        assertEquals(110, response.getMonthlyPoints().get(YearMonth.of(2025, 7)));
        assertEquals(155, response.getTotalPoints());
    }

    @Test
    void testGetRewardsByCustomerId_C3() {
        CustomerRewardsResponse response = rewardsService.getRewardsByCustomerId("C3");

        assertEquals("C3", response.getCustomerId());
        assertEquals(2, response.getMonthlyPoints().size());
        assertEquals(0, response.getMonthlyPoints().get(YearMonth.of(2025, 6)));
        assertEquals(60, response.getMonthlyPoints().get(YearMonth.of(2025, 7)));
        assertEquals(60, response.getTotalPoints());
    }

    @Test
    void testGetAllCustomerRewards() {
        List<CustomerRewardsResponse> all = rewardsService.getAllCustomerRewards();
        assertEquals(3, all.size());
        assertTrue(all.stream().anyMatch(r -> r.getCustomerId().equals("C1")));
        assertTrue(all.stream().anyMatch(r -> r.getCustomerId().equals("C2")));
        assertTrue(all.stream().anyMatch(r -> r.getCustomerId().equals("C3")));
    }

    @Test
    void testInvalidCustomerId() {
        assertThrows(InvalidTransactionException.class, () ->
                rewardsService.getRewardsByCustomerId("UNKNOWN"));
    }

    @Test
    void testNullCustomerId() {
        assertThrows(InvalidTransactionException.class, () ->
                rewardsService.getRewardsByCustomerId(null));
    }
}
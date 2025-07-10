package com.rewards.point.service;

import com.rewards.point.dto.CustomerRewardsResponse;
import com.rewards.point.exception.CustomerNotFoundException;
import com.rewards.point.exception.NegativeAmountException;
import com.rewards.point.exception.TransactionNotFoundException;
import com.rewards.point.model.Transaction;
import com.rewards.point.repository.RewardsRepository;
import com.rewards.point.service.RewardsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardsServiceTest {

    private RewardsRepository rewardsRepository;
    private RewardsService rewardsService;

    @BeforeEach
    void setUp() {
        rewardsRepository = mock(RewardsRepository.class);
        rewardsService = new RewardsService(rewardsRepository);
    }

    @Test
    void testGetAllCustomerRewardsWithin3Months() {
        List<Transaction> transactions = List.of(
                new Transaction("C1", 120, LocalDate.now().minusDays(10)),
                new Transaction("C1", 80, LocalDate.now().minusMonths(2)),
                new Transaction("C2", 60, LocalDate.now().minusMonths(4)) // should be filtered out
        );
        when(rewardsRepository.getMockedTransactions()).thenReturn(transactions);

        List<CustomerRewardsResponse> result = rewardsService.getAllCustomerRewards();

        assertEquals(1, result.size()); // C2 should be filtered out
        CustomerRewardsResponse c1Response = result.get(0);
        assertEquals("C1", c1Response.getCustomerId());
        assertEquals(120, c1Response.getTotalPoints());
        assertEquals(2, c1Response.getMonthlyPoints().size());
    }

    @Test
    void testGetRewardsByCustomerId_withValidTransactions() {
        List<Transaction> transactions = List.of(
                new Transaction("C1", 120, LocalDate.now().minusDays(10)),
                new Transaction("C1", 70, LocalDate.now().minusMonths(1))
        );
        when(rewardsRepository.getMockedTransactions()).thenReturn(transactions);

        CustomerRewardsResponse response = rewardsService.getRewardsByCustomerId("C1");

        assertEquals("C1", response.getCustomerId());
        assertEquals(110, response.getTotalPoints());
        assertEquals(1, response.getMonthlyPoints().size());
    }

    @Test
    void testGetRewardsByCustomerId_nullId_throwsException() {
        assertThrows(CustomerNotFoundException.class, () -> {
            rewardsService.getRewardsByCustomerId(null);
        });
    }

    @Test
    void testGetRewardsByCustomerId_emptyTransactions_throwsException() {
        List<Transaction> transactions = List.of(
                new Transaction("C1", 50, LocalDate.now().minusMonths(5)) // old, should be filtered
        );
        when(rewardsRepository.getMockedTransactions()).thenReturn(transactions);

        assertThrows(TransactionNotFoundException.class, () -> {
            rewardsService.getRewardsByCustomerId("C1");
        });
    }

    @Test
    void testNegativeTransactionAmountThrowsException() {
        List<Transaction> transactions = List.of(
                new Transaction("C1", -50, LocalDate.now())
        );
        when(rewardsRepository.getMockedTransactions()).thenReturn(transactions);

        assertThrows(NegativeAmountException.class, () -> {
            rewardsService.getRewardsByCustomerId("C1");
        });
    }
}


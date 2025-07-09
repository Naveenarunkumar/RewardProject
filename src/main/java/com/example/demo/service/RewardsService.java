package com.example.demo.service;
import com.example.demo.dto.CustomerRewardsResponse;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.NegativeAmountException;
import com.example.demo.exception.TransactionNotFoundException;
import com.example.demo.model.Transaction;
import com.example.demo.repository.RewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class responsible for calculating reward points for customers.
 */
@Service
public class RewardsService {

    /**
     * Retrieves reward points for all customers.
     *
     * @return list of reward summaries
     */
    private final RewardsRepository rewardsRepository;

    @Autowired
    public RewardsService(RewardsRepository rewardsRepository){
        this.rewardsRepository = rewardsRepository;
    }
    public List<CustomerRewardsResponse> getAllCustomerRewards() {
        List<Transaction> allTransactions = rewardsRepository.getMockedTransactions();

        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);

        List<Transaction> recentTransactions = allTransactions.stream()
                .filter(tx -> !tx.getDate().isBefore(threeMonthsAgo))
                .toList();

        Map<String, List<Transaction>> grouped = recentTransactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));

        return grouped.entrySet().stream()
                .map(entry -> calculateRewards(entry.getKey(), entry.getValue()))
                .toList();
    }



    /**
     * Retrieves reward points for a specific customer.
     *
     * @param customerId the ID of the customer
     * @return reward summary for the customer
     */
    public CustomerRewardsResponse getRewardsByCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new CustomerNotFoundException("Customer ID must not be null or empty.");
        }

        List<Transaction> allTransactions = rewardsRepository.getMockedTransactions();

        // Filter by customer and last 3 months
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<Transaction> customerTx = allTransactions.stream()
                .filter(tx -> tx.getCustomerId().equalsIgnoreCase(customerId))
                .filter(tx -> !tx.getDate().isBefore(threeMonthsAgo))
                .toList();

        if (customerTx.isEmpty()) {
            throw new TransactionNotFoundException("No recent transactions found for customer: " + customerId);
        }

        return calculateRewards(customerId, customerTx);
    }


    /**
     * Calculates reward points for a customer based on their transactions.
     *
     * @param customerId the customer ID
     * @param transactions list of transactions
     * @return reward summary
     */
    private CustomerRewardsResponse calculateRewards(String customerId, List<Transaction> transactions) {
        Map<YearMonth, Integer> monthlyPoints = new HashMap<>();

        for (Transaction tx : transactions) {
            if (tx.getAmount() < 0) {
                throw new NegativeAmountException("Transaction amount cannot be negative.");
            }

            YearMonth month = YearMonth.from(tx.getDate());
            int points = calculatePoints(tx.getAmount());

            monthlyPoints.merge(month, points, Integer::sum);
        }

        int totalPoints = monthlyPoints.values().stream().mapToInt(Integer::intValue).sum();

        return new CustomerRewardsResponse(customerId, monthlyPoints, totalPoints);
    }

    /**
     * Calculates reward points for a single transaction.
     *
     * @param amount the transaction amount
     * @return reward points
     */
    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (int) ((amount - 100) * 2);
            points += 50; // for $50-$100
        } else if (amount > 50) {
            points += (int) (amount - 50);
        }
        return points;
    }

    /**
     * Returns a mock list of transactions for demonstration purposes.
     *
     * @return list of transactions
     */

}

package com.rewards.point.service;
import com.rewards.point.dto.CustomerRewardsResponse;
import com.rewards.point.exception.CustomerNotFoundException;
import com.rewards.point.exception.NegativeAmountException;
import com.rewards.point.exception.TransactionNotFoundException;
import com.rewards.point.model.Transaction;
import com.rewards.point.repository.RewardsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    /**
     * Constructs a new {@code RewardsService} with the specified repository.
     *
     * @param rewardsRepository the repository used to fetch transaction data
     */

    @Autowired
    public RewardsService(RewardsRepository rewardsRepository){
        this.rewardsRepository = rewardsRepository;
    }
    
    /**
     * Retrieves reward summaries for all customers based on transactions
     * from the last three months.
     *
     * @return a list of {@link CustomerRewardsResponse} objects representing
     *         reward summaries for each customer
     */

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
     * Retrieves the reward summary for a specific customer based on their
     * transactions from the last three months.
     *
     * @param customerId the ID of the customer
     * @return a {@link CustomerRewardsResponse} containing the reward summary
     * @throws CustomerNotFoundException if the customer ID is null or empty
     * @throws TransactionNotFoundException if no recent transactions are found
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
     * Calculates the reward summary for a customer based on their transactions.
     * Points are calculated per month and totaled.
     *
     * @param customerId the ID of the customer
     * @param transactions the list of transactions for the customer
     * @return a {@link CustomerRewardsResponse} with monthly and total points
     * @throws NegativeAmountException if any transaction has a negative amount
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
     * Calculates reward points for a single transaction based on the amount.
     * <ul>
     *   <li>2 points for every dollar spent over $100</li>
     *   <li>1 point for every dollar spent between $50 and $100</li>
     * </ul>
     *
     * @param amount the transaction amount
     * @return the calculated reward points
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

    
}


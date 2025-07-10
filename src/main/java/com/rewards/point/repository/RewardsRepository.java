package com.rewards.point.repository;

import org.springframework.stereotype.Repository;

import com.rewards.point.model.Transaction;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository class that provides access to transaction data.
 * <p>
 * This implementation returns a hardcoded list of mock transactions
 * for demonstration or testing purposes.
 * </p>
 */

@Repository
public class RewardsRepository {
	/**
     * Retrieves a list of mocked customer transactions.
     * <p>
     * Each transaction includes a customer ID, transaction amount, and transaction date.
     * This mock data simulates real-world transactions for testing reward point calculations.
     * </p>
     *
     * @return a list of {@link Transaction} objects representing sample customer transactions
     */

    public List<Transaction> getMockedTransactions() {
        return List.of(
                new Transaction("C1", 120, LocalDate.of(2025, 6, 1)),
                new Transaction("C1", 75, LocalDate.of(2025, 5, 15)),
                new Transaction("C1", 200, LocalDate.of(2025, 4, 10)),
                new Transaction("C1", 200, LocalDate.of(2025, 3, 10)),
                new Transaction("C2", 95, LocalDate.of(2025, 5, 5)),
                new Transaction("C2", 130, LocalDate.of(2025, 7, 20)),
                new Transaction("C3", 45, LocalDate.of(2025, 4, 25)) // No points
        );
    }
}


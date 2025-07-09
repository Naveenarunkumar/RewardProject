package com.example.demo.repository;

import com.example.demo.model.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RewardsRepository {
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

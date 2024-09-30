package com.pasod.financetracker.repository;

import com.pasod.financetracker.model.Transaction;
import com.pasod.financetracker.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findByUser(User user, Pageable pageable);

    @Query(
            nativeQuery = true,
            value = "select sum(amount) from transaction_details td where td.is_income = true"
    )
    Optional<BigDecimal> totalIncome();

    @Query(
            nativeQuery = true,
            value = "select sum(amount) from transaction_details td where td.is_income = false"
    )
    Optional<BigDecimal> totalExpense();
}

package com.pasod.financetracker.repository;

import com.pasod.financetracker.model.Item;
import com.pasod.financetracker.model.ItemTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTransactionRepository extends JpaRepository<ItemTransaction, Long> {

    boolean existsByItem(Item item);
}

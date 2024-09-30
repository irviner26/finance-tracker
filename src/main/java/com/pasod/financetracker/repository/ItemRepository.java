package com.pasod.financetracker.repository;

import com.pasod.financetracker.model.Item;
import com.pasod.financetracker.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

    List<Item> findItemsByPlan(Plan plan);
}

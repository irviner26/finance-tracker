package com.pasod.financetracker.repository;

import com.pasod.financetracker.model.Item;
import com.pasod.financetracker.model.User;
import com.pasod.financetracker.model.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    boolean existsByUserAndItem(User user, Item item);

    Optional<UserItem> findByUserAndItem(User user, Item item);
}

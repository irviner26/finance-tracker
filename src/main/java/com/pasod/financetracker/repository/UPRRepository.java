package com.pasod.financetracker.repository;

import com.pasod.financetracker.model.Plan;
import com.pasod.financetracker.model.Role;
import com.pasod.financetracker.model.User;
import com.pasod.financetracker.model.UserPlanRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UPRRepository extends JpaRepository<UserPlanRole, Long> {

    boolean existsByUserAndPlan(User user, Plan plan);

    List<UserPlanRole> findByPlan(Plan plan);

    List<UserPlanRole> findByUser(User user);

    List<UserPlanRole> findByUserAndRole(User user, Role role);

    void deleteAllByPlan(Plan plan);
}

package com.pasod.financetracker.service;

import com.pasod.financetracker.dto.request.CreatePlanRequest;
import com.pasod.financetracker.dto.request.EditPlanRequest;
import com.pasod.financetracker.dto.response.PlanResponse;
import com.pasod.financetracker.handler.ValidationHandler;
import com.pasod.financetracker.model.Plan;
import com.pasod.financetracker.model.UserPlanRole;
import com.pasod.financetracker.repository.PlanRepository;
import com.pasod.financetracker.repository.RoleRepository;
import com.pasod.financetracker.repository.UPRRepository;
import com.pasod.financetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanServiceIMPL implements PlanService{

    public static final String ROLE_NOT_FOUND_INITIATED = "Role not found/initiated";

    private final PlanRepository planRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UPRRepository uprRepository;

    private final ValidationHandler validationHandler;

    @Override
    public PlanResponse createPlan(CreatePlanRequest request) {
        validationHandler.validate(request);

        var creatorRole = roleRepository
                .findByName("CREATOR")
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ROLE_NOT_FOUND_INITIATED
                        )
                );

        var plan = Plan
                .builder()
                .name(request.name())
                .description(request.description())
                .build();

        var upr = UserPlanRole
                .builder()
                .user(null)
                .plan(plan)
                .role(creatorRole)
                .build();

        planRepository.save(plan);

        uprRepository.save(upr);
        
        return toPlanResponse(plan);
    }

    @Override
    @Transactional
    public PlanResponse editPlan(EditPlanRequest request, String id) {
        var plan = planRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                PLAN_NOT_FOUND(id)
                        )
                );

        if (!request.name().isBlank()) {
            plan.setName(request.name());
        }

        if (!request.description().isBlank()) {
            plan.setDescription(request.description());
        }

        return toPlanResponse(plan);
    }

    @Override
    public PlanResponse getPlan(String id) {
        var plan = planRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                PLAN_NOT_FOUND(id)
                        )
                );

        return toPlanResponse(plan);
    }

    @Override
    public List<PlanResponse> getPlans(String role) {
        if (role.isBlank()) {
            return uprRepository
                    .findByUser(null)
                    .stream()
                    .map(UserPlanRole::getPlan)
                    .map(PlanServiceIMPL::toPlanResponse)
                    .toList();
        }

        var roleEntity = roleRepository
                .findByName(role)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ROLE_NOT_FOUND_INITIATED
                        )
                );

        return uprRepository
                .findByUserAndRole(null, roleEntity)
                .stream()
                .map(UserPlanRole::getPlan)
                .map(PlanServiceIMPL::toPlanResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deletePlan(String id) {
        var plan = planRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                PLAN_NOT_FOUND(id)
                        )
                );

        uprRepository.deleteAllByPlan(plan);

        planRepository.delete(plan);
    }

    private static String PLAN_NOT_FOUND(String id) {
        return "Plan with id: " + id + " does not exist";
    }

    private static String USER_NOT_FOUND(String username) {
        return "User: " + username + " not found";
    }

    private static PlanResponse toPlanResponse(Plan plan) {
        return new PlanResponse(
                plan.getId().toString(),
                plan.getName(),
                plan.getDescription(),
                null
        );
    }
}

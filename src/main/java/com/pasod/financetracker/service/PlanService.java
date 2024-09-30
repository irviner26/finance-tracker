package com.pasod.financetracker.service;

import com.pasod.financetracker.dto.request.CreatePlanRequest;
import com.pasod.financetracker.dto.request.EditPlanRequest;
import com.pasod.financetracker.dto.response.PlanResponse;

import java.util.List;

public interface PlanService {

    PlanResponse createPlan(CreatePlanRequest request);

    PlanResponse editPlan(EditPlanRequest request, String id);

    PlanResponse getPlan(String id);

    List<PlanResponse> getPlans(String role);

    void deletePlan(String id);
}

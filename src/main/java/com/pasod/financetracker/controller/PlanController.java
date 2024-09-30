package com.pasod.financetracker.controller;

import com.pasod.financetracker.dto.request.CreatePlanRequest;
import com.pasod.financetracker.dto.request.EditPlanRequest;
import com.pasod.financetracker.dto.response.PlanResponse;
import com.pasod.financetracker.dto.response.WebResponse;
import com.pasod.financetracker.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/plan")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping(
            path = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<PlanResponse>> createPlan(
            @RequestBody CreatePlanRequest request
    ) {
        var response = planService.createPlan(request);

        return responseEntity(
                response,
                "Successfully created plan",
                null,
                null
        );
    }

    @PatchMapping(
            path = "/edit/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<PlanResponse>> editPlan(
            @RequestBody EditPlanRequest request,
            @PathVariable(value = "id") String id
    ) {
        var response = planService.editPlan(request, id);

        return responseEntity(
                response,
                "Successfully edited plan details",
                null,
                null
        );
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<PlanResponse>> getPlan(
            @PathVariable(value = "id") String id
    ) {
        var response = planService.getPlan(id);

        return responseEntity(
                response,
                "Successfully retrieved plan details",
                null,
                null
        );
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<List<PlanResponse>>> getPlans(
            @RequestParam(value = "role") String role
    ) {
        var responses = planService.getPlans(role);

        return responseEntity(
                responses,
                "Successfully retrieved plans",
                null,
                null
        );
    }

    @DeleteMapping(
            path = "/delete/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> deletePlan(
            @PathVariable(value = "id") String id
    ) {
        planService.deletePlan(id);

        return responseEntity(
                "Deleted ".concat(id),
                "Successfully deleted plan",
                null,
                null
        );
    }

    private static <T> ResponseEntity<WebResponse<T>> responseEntity(
            T response,
            String message,
            Integer page,
            Integer size
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        new WebResponse<>(
                                response,
                                HttpStatus.OK.value(),
                                message,
                                null,
                                page,
                                size
                        )
                );
    }
}

package com.pasod.financetracker.service;

import com.pasod.financetracker.dto.request.CreateItemRequest;
import com.pasod.financetracker.dto.request.UpdateItemRequest;
import com.pasod.financetracker.dto.response.ItemResponse;
import com.pasod.financetracker.handler.ValidationHandler;
import com.pasod.financetracker.model.Item;
import com.pasod.financetracker.repository.ItemRepository;
import com.pasod.financetracker.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceIMPL implements ItemService{

    private final ItemRepository itemRepository;

    private final ValidationHandler validationHandler;

    private final PlanRepository planRepository;

    @Override
    public ItemResponse createItem(CreateItemRequest request, String planId) {
        validationHandler.validate(request);

        var plan = planRepository
                .findById(UUID.fromString(planId))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                PLAN_NOT_FOUND(planId)
                        )
                );

        var item = Item
                .builder()
                .name(request.name())
                .description(request.description())
                .target(request.target())
                .paid(BigDecimal.valueOf(0.00))
                .plan(plan)
                .build();

        itemRepository.save(item);

        return toItemResponse(item);
    }

    @Override
    public ItemResponse getItem(String id) {
        var item = itemRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ITEM_NOT_FOUND(id)
                        )
                );

        return toItemResponse(item);
    }

    @Override
    public List<ItemResponse> getItems(String planId) {
        var plan = planRepository
                .findById(UUID.fromString(planId))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                PLAN_NOT_FOUND(planId)
                        )
                );

        return itemRepository
                .findItemsByPlan(plan)
                .stream()
                .map(ItemServiceIMPL::toItemResponse)
                .toList();
    }

    @Override
    @Transactional
    public ItemResponse updateItem(UpdateItemRequest request, String id) {
        var item = itemRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ITEM_NOT_FOUND(id)
                        )
                );

        if (!request.name().isBlank()) {
            item.setName(request.name());
        }

        if (!request.description().isBlank()) {
            item.setDescription(request.description());
        }

        if (request.target() != null && !request.target().equals(item.getTarget())) {
            item.setTarget(request.target());
        }

        if (request.paid() != null && !request.paid().equals(item.getPaid())) {
            item.setPaid(request.paid());
        }

        itemRepository.save(item);

        return toItemResponse(item);
    }

    @Override
    @Transactional
    public void deleteItem(String id) {
        var item = itemRepository
                .findById(UUID.fromString(id))
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                ITEM_NOT_FOUND(id)
                        )
                );

        itemRepository.delete(item);
    }

    private static String PLAN_NOT_FOUND(String planId) {
        return "Plan: " + planId + "not found";
    }

    private static String ITEM_NOT_FOUND(String id) {
        return "Item: " + id + " not found";
    }

    private static ItemResponse toItemResponse(Item item) {
        return new ItemResponse(
                item.getId().toString(),
                item.getName(),
                item.getDescription(),
                item.getTarget(),
                item.getPaid()
        );
    }
}

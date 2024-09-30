package com.pasod.financetracker.service;

import com.pasod.financetracker.dto.request.CreateItemRequest;
import com.pasod.financetracker.dto.request.UpdateItemRequest;
import com.pasod.financetracker.dto.response.ItemResponse;

import java.util.List;

public interface ItemService {

    ItemResponse createItem(CreateItemRequest request, String planId);

    ItemResponse getItem(String id);

    List<ItemResponse> getItems(String planId);

    ItemResponse updateItem(UpdateItemRequest request, String id);

    void deleteItem(String id);
}

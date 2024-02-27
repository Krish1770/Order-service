package com.example.orderservice.repository.service.impl;

import com.example.orderservice.entity.Items;
import com.example.orderservice.repository.ItemRepository;
import com.example.orderservice.repository.service.ItemRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ItemRepoServiceImpl implements ItemRepoService {

    @Autowired
    private ItemRepository itemRepository;
    @Override
    public Optional<Items> findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public Optional<List<Items>> findByItemIdIn(Set<Long> itemIdList) {
        return itemRepository.findByItemIdIn(itemIdList);
    }
}

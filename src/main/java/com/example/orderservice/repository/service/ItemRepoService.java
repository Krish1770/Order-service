package com.example.orderservice.repository.service;

import com.example.orderservice.entity.Items;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface ItemRepoService {
    Optional<Items> findById(Long itemId);

    Optional<List<Items>> findByItemIdIn(Set<Long> longs);
}

package com.example.orderservice.repository.service;

import com.example.orderservice.entity.Items;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ItemRepoService {
    Optional<Items> findById(Long itemId);
}

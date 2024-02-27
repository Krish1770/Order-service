package com.example.orderservice.repository.service;


import com.example.orderservice.entity.Customer;
import com.example.orderservice.entity.TenantDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TenantRepoService {
    Optional<TenantDetails> findByName(String name);
}

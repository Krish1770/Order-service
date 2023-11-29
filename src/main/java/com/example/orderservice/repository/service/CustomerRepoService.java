package com.example.orderservice.repository.service;

import com.example.orderservice.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerRepoService {
    Optional<Customer> findById(Long customerId);
}

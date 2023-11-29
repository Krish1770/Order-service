package com.example.orderservice.repository.service.impl;

import com.example.orderservice.entity.Customer;
import com.example.orderservice.repository.CustomerRepository;
import com.example.orderservice.repository.service.CustomerRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRepoServiceImpl  implements CustomerRepoService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }
}

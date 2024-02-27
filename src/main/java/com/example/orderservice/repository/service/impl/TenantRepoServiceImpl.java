package com.example.orderservice.repository.service.impl;

import com.example.orderservice.entity.TenantDetails;
import com.example.orderservice.repository.TenantRepository;
import com.example.orderservice.repository.service.TenantRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantRepoServiceImpl  implements TenantRepoService {

    @Autowired
    private TenantRepository tenantRepository;
    @Override
    public Optional<TenantDetails> findByName(String name) {
        return tenantRepository.findByName(name);
    }
}

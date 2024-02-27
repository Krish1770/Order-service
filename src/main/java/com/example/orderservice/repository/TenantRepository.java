package com.example.orderservice.repository;


import com.example.orderservice.entity.TenantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantDetails,String> {
    Optional<TenantDetails> findByName(String name);
}

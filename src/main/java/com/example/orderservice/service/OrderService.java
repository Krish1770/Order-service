package com.example.orderservice.service;


import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.dto.TenantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
public interface OrderService {
    ResponseEntity<OrderResponseDto> add(OrderDto orderDTO);

    String login(LoginDto loginDTO);

    String tenantLogin(TenantDto tenantDto);
}

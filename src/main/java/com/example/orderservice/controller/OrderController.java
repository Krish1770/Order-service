package com.example.orderservice.controller;

import com.example.orderservice.api.OrderApi;
import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.dto.TenantDto;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements OrderApi {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<OrderResponseDto> add(OrderDto orderDTO) {
        return orderService.add(orderDTO);

    }

    @Override
    public String login(LoginDto loginDTO) {
        return orderService.login(loginDTO);
    }

    @Override
    public String tenantLogin(TenantDto tenantDto) {
        return orderService.tenantLogin(tenantDto);
    }
}

package com.example.orderservice.repository.service.impl;

import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.service.OrderRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class OrderRepoServiceImpl implements OrderRepoService {

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Order save(Order order) {
     orderRepository.save(order);
        return order;
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return orderRepository.findById(orderId);
    }
}

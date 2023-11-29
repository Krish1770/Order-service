package com.example.orderservice.repository.service.impl;

import com.example.orderservice.entity.OrderItems;
import com.example.orderservice.repository.OrderItemsRepository;
import com.example.orderservice.repository.service.OrderItemsRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderItemsRepoServiceImpl implements OrderItemsRepoService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Override
    public OrderItems save(OrderItems orderItem) {
     return orderItemsRepository.save(orderItem);
    }
}

package com.example.orderservice.repository.service;

import com.example.orderservice.entity.OrderItems;
import org.springframework.stereotype.Service;


@Service
public interface OrderItemsRepoService {
    OrderItems save(OrderItems orderItem);
}

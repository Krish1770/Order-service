package com.example.orderservice.service;


import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public interface OrderService {
    ResponseEntity<ResponseDTO> add(OrderDTO orderDTO);

}

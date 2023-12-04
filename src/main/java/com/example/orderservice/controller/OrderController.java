package com.example.orderservice.controller;

import com.example.orderservice.api.OrderApi;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.ResponseDTO;
import com.example.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
public class OrderController implements OrderApi {

    @Autowired
    private OrderService orderService;
    @Override
    public ResponseEntity<ResponseDTO> add(OrderDTO orderDTO) throws ExecutionException, InterruptedException, TimeoutException {
        return orderService.add(orderDTO);
    }
}

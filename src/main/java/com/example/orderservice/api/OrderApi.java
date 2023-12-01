package com.example.orderservice.api;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("order")
public interface OrderApi {


    @PostMapping()
    ResponseEntity<ResponseDTO> add(@RequestBody OrderDTO orderDTO);



}

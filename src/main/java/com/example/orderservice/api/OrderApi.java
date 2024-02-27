package com.example.orderservice.api;

import com.example.orderservice.dto.LoginDto;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.OrderResponseDto;
import com.example.orderservice.dto.TenantDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping(value = "order")
public interface OrderApi {


    @PostMapping()
    ResponseEntity<OrderResponseDto> add(@RequestBody OrderDto orderDTO) ;

    @PostMapping(value = "/login")
    String login(@RequestBody LoginDto loginDTO);

    @PostMapping(value = "/TenantLogin")
    String tenantLogin(@RequestBody TenantDto tenantDto);


}

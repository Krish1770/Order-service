package com.example.orderservice.feignClient;

import com.example.orderservice.dto.BillDto;
import com.example.orderservice.dto.OrderResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "PROXY-SERVICE",url = "http://localhost:8082")
public interface ProxyCollaboration {


    @PostMapping("Proxy")
    ResponseEntity<OrderResponseDto> createBills(@RequestBody  BillDto billDto);
}

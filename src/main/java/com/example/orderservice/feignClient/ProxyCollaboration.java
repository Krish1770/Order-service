package com.example.orderservice.feignClient;

import com.example.orderservice.dto.BillDto;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@FeignClient(name = "PROXY-SERVICE",url = "http://localhost:8082")
public interface ProxyCollaboration {


    @PostMapping("Proxy")
    ResponseEntity<ResponseDTO> createBills( @RequestBody  BillDto billDto);
}

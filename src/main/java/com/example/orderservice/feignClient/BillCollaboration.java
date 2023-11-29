package com.example.orderservice.feignClient;


import com.example.orderservice.dto.BillDto;
import com.example.orderservice.dto.ResponseDTO;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "BILL-SERVICE",url ="http://localhost:8081" )
public interface BillCollaboration {



    @PostMapping("Bill")
    ResponseEntity<ResponseDTO> createBills(@RequestBody BillDto billDto);
}

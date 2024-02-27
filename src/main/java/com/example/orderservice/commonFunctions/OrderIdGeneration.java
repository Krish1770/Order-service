package com.example.orderservice.commonFunctions;

import com.example.orderservice.repository.service.OrderRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class OrderIdGeneration {

    @Autowired
    private OrderRepoService orderRepoService;

    public String orderIdGenerator() {

    String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    Random random = new Random();

    String orderId = "";

    while (orderId.isEmpty() || orderRepoService.findById(orderId).isPresent()) {
        orderId = "";
        int initial = random.nextInt(100);
        orderId += initial;
        for (int i = 0; i < 4; i++) {
            int k = random.nextInt(52);

            orderId += alphabets.charAt(k);
        }

        int last = random.nextInt(100);
        orderId += last;

        System.out.println(orderId);
    }
    return orderId;
}


}

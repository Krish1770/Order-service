package com.example.orderservice.service.impl;

import com.example.orderservice.commonFunctions.OrderIdGeneration;
import com.example.orderservice.dto.*;
import com.example.orderservice.entity.*;
import com.example.orderservice.feignClient.BillCollaboration;
import com.example.orderservice.feignClient.ProxyCollaboration;
import com.example.orderservice.repository.service.*;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderIdGeneration orderIdGeneration;
    @Autowired
    private ProxyCollaboration proxyCollaboration;
    @Autowired
    private ItemRepoService itemRepoService;
    @Autowired
    private CustomerRepoService customerRepoService;

    @Autowired
    private TenantRepoService tenantRepoService;

    @Autowired
    private OrderRepoService orderRepoService;

    @Autowired
    private OrderItemsRepoService orderItemsRepoService;

    @Autowired
    private BillCollaboration billCollaboration;
    private LinkedHashMap<Long, String> itemResult;

    @Override

    public ResponseEntity<OrderResponseDto> add(OrderDto orderDTO) {


        List<BigDecimal> gstList = new ArrayList<>();
        Order order = new Order();

        order.setOrderedDate(new Date());

        if (orderDTO.getCustomerId() == null)
            return (ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderResponseDto(HttpStatus.NOT_FOUND, "customerId not present", "")));


        Order savedOrder = new Order();
        List<OrderItems> savedOrderItems = new ArrayList<>();
        OrderItems orderItems = new OrderItems();
        Optional<Customer> customer = customerRepoService.findById(orderDTO.getCustomerId());

        if (customer.isPresent()) {
            order.setCustomer(customer.get());
//
            String orderId = orderIdGeneration.orderIdGenerator();

            order.setOrderId(orderId);

            savedOrder = orderRepoService.save(order);


            Double totalAmt = 0.0;
           itemResult = new LinkedHashMap<>();
            if (orderDTO.getProducts().keySet().isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderResponseDto(HttpStatus.NOT_FOUND, "orderList is Empty", ""));
            Optional<List<Items>> itemsList = itemRepoService.findByItemIdIn(orderDTO.getProducts().keySet());
            List<Long> itemsIdList = new ArrayList<>();
            itemsList.get().forEach(eachItems -> itemsIdList.add(eachItems.getItemId()));

            if (itemsList.get().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderResponseDto(HttpStatus.NOT_FOUND, "all the product ids given are invalid", ""));
            }
            for (Long productId : orderDTO.getProducts().keySet()) {
                OrderItems orderItem = new OrderItems();
                if (itemsIdList.contains(productId))
                {
                    int indexOfTheitem = itemsIdList.indexOf(productId);
                    Items resultItem = itemsList.get().get(indexOfTheitem);
                    itemResult.put(resultItem.getItemId(), "itemSaved");
                    orderItem.setQuantity(orderDTO.getProducts().get(productId));
                    orderItem.setOrder(savedOrder);
                    orderItem.setItem(resultItem);

                    Double total = 0.0;

                    total += (resultItem.getPrice() * orderDTO.getProducts().get(productId));
                    total += ((resultItem.getCategory().getGst().doubleValue() * (resultItem.getPrice() / 100.0)) * (orderDTO.getProducts().get(productId)));
                    orderItem.setAmount(resultItem.getPrice());

                    gstList.add(resultItem.getCategory().getGst());
                    totalAmt += total;
                    orderItem = orderItemsRepoService.save(orderItem);

                    savedOrderItems.add(orderItem);

                } else
                    itemResult.put(productId, "not saved");


            }


            savedOrder.setAmount(totalAmt);
            orderRepoService.save(savedOrder);


        } else
            return (ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderResponseDto(HttpStatus.NOT_FOUND, "user not found", "")));

        BillDto billDto = new BillDto(customer.get().getName(), savedOrder, savedOrderItems, gstList, customer.get().getEmail());


      log.info(billDto.getOrder().getAmount()+"");

      log.info(String.valueOf(billDto));

        if (!billDto.getOrderItems().isEmpty()) {
            String result = (proxyCollaboration.createBills(billDto).getBody()).getData().toString();
            System.out.println(result);

           log.info("val" + result);
//

            savedOrder.setBillId(result);
            orderRepoService.save(savedOrder);


            return (ResponseEntity.status(HttpStatus.OK).body(new OrderResponseDto(HttpStatus.OK, "order saved", savedOrder.getOrderId())));

        } else
            return (ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OrderResponseDto(HttpStatus.NOT_FOUND, "order not saved", "")));

    }

    @Override
    public String login(LoginDto loginDTO) {
        Optional<Customer> customer=customerRepoService.findByEmail(loginDTO.getEmail());
        System.out.println(customer+"  "+loginDTO);
        return customer.map(value -> value.getCustomerId() + "").orElse("invalid login");
    }

    @Override
    public String tenantLogin(TenantDto tenantDto) {
        Optional<TenantDetails> tenantDetails =tenantRepoService.findByName(tenantDto.getName());
        System.out.println(tenantDetails+"  "+tenantDto);
        if(tenantDetails.isPresent())
        {
            if(tenantDetails.get().getPassword().equals(tenantDto.getPassword()))
            {
                return tenantDetails.get().getTenantId();
            }
            return "incorrect password";
        }
        else
            return "invalid login";
    }
}

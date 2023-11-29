package com.example.orderservice.service.impl;

import com.example.orderservice.dto.BillDto;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.dto.ResponseDTO;
import com.example.orderservice.entity.Customer;
import com.example.orderservice.entity.Items;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItems;
import com.example.orderservice.feignClient.BillCollaboration;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.service.CustomerRepoService;
import com.example.orderservice.repository.service.ItemRepoService;
import com.example.orderservice.repository.service.OrderItemsRepoService;
import com.example.orderservice.repository.service.OrderRepoService;
import com.example.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.experimental.PackagePrivate;
import org.aspectj.apache.bcel.classfile.Module;
import org.aspectj.apache.bcel.classfile.SourceFile;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private ItemRepoService itemRepoService;
    @Autowired
    private CustomerRepoService customerRepoService;

    @Autowired
    private OrderRepoService orderRepoService;

    @Autowired
    private OrderItemsRepoService orderItemsRepoService;

   @Autowired
   private  BillCollaboration billCollaboration;
    @Override
    public ResponseEntity<ResponseDTO> add(OrderDTO orderDTO) {


        List<BigDecimal> gstList=new ArrayList<>();
        Order order=new Order();

        order.setOrderedDate(new Date());

        if(orderDTO.getCustomerId() == null)
            return (ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(HttpStatus.NOT_FOUND,"customerId not present","")));


        Order savedOrder=new Order();
        List<OrderItems>  savedOrderItems=new ArrayList<>();
        OrderItems orderItems=new OrderItems();
        Optional<Customer> customer = isCustomerExist(orderDTO.getCustomerId());

        if(customer.isPresent())
        {
            order.setCustomer(customer.get());
//
            String orderId=orderIdGenerator();

            order.setOrderId(orderId);

          savedOrder= orderRepoService.save(order);




         Double totalAmt=0.0;
            LinkedHashMap<Long,String> itemResult=new LinkedHashMap<>();
            for(Long i:orderDTO.getProducts().keySet())
            {
                OrderItems orderItem=new OrderItems();
               Optional<Items> resultItem= isItemExist(i);
              if(resultItem.isPresent())
              {

                  itemResult.put(resultItem.get().getItemId(), "itemSaved");
                  assert orderItem != null;
                  orderItem.setQuantity(orderDTO.getProducts().get(i));
                  orderItem.setOrder(savedOrder);
                  orderItem.setItem(resultItem.get());

               Double total=0.0;

                  total+=(resultItem.get().getPrice()*orderDTO.getProducts().get(i));
                  total+=(resultItem.get().getCategory().getGst().doubleValue()*(orderDTO.getProducts().get(i)));
                  orderItem.setAmount(resultItem.get().getPrice());

                  gstList.add(resultItem.get().getCategory().getGst());
                  totalAmt+= total;
                 orderItem =orderItemsRepoService.save(orderItem);

                 savedOrderItems.add(orderItem);

              }

              else
                itemResult.put(i, "not saved");

              savedOrderItems.forEach(System.out::println);
            }
            savedOrder.setAmount(totalAmt);
            orderRepoService.save(savedOrder);

            System.out.println(itemResult);
        }

        BillDto billDto=new BillDto(customer.get().getName(),savedOrder,savedOrderItems,gstList,customer.get().getEmail());

        JSONObject jsonObject=new JSONObject(billDto);

        System.out.println(billDto.getOrder().getAmount());
//   billCollaboration.createBills(jsonObject);
String id=billCollaboration.createBills( billDto).getBody().getData().toString();
        System.out.println(id);
 savedOrder.setBillId(id);
 orderRepoService.save(savedOrder);
        return (ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.OK,"order saved",savedOrder.getOrderId())));

    }

    private String orderIdGenerator() {


        String alphabets="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";





        Random random=new Random();

       String orderId="";

        while((orderId.equals("") )||(!orderId.isEmpty() && orderRepoService.findById(orderId).isPresent())) {
           orderId="";
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

    private Optional<Customer> isCustomerExist(Long customerId) {

        return customerRepoService.findById(customerId);
    }

    private Optional<Items> isItemExist(Long itemId)
    {
        return itemRepoService.findById(itemId);
    }
}

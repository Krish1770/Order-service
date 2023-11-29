package com.example.orderservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrderDetails")
public class Order {

    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    private String orderId;

    @ManyToOne(targetEntity = Customer.class, cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName = "customerId",name ="customer")
    private Customer customer;

    private String billId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date")
    private Date orderedDate;


}

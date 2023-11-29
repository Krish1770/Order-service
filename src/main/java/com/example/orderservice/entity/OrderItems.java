package com.example.orderservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OrderItems")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = Order.class,cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName ="orderId",name = "orderId")
    private Order order;

    @ManyToOne(targetEntity = Items.class,cascade = CascadeType.MERGE)
    @JoinColumn(referencedColumnName ="itemId",name = "item")
    private Items item;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "amount")
    private Long amount;


}

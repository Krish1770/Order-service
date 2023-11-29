package com.example.orderservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Items")

public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;


    @Column(name = "itemName")
    private String name;

    @Column(name = "price")
    private Long price;

    @Column(name = "description")
    private String desc;

    @ManyToOne(targetEntity = Category.class,cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "typeId",name = "type")
    private Category category;

}

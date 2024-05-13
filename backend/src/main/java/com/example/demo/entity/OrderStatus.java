package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public byte id;

    @Column(name = "name_status")
    public String status;

    @Column(name = "icon")
    public String iconClass;
}

package com.example.demo.entity;
import javax.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "tracking_code")
    public String code;

    @Column(name = "total_amount")
    public double total;

}

package com.example.demo.entity;
import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "product")
    public Product product;

    @Column(name = "quantity")
    public int quantityCart;

    @Column(name = "created_at")
    public Timestamp createdAt;




}

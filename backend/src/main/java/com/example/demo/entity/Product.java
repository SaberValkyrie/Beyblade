package com.example.demo.entity;
import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    public long productId;

    @Column(name = "name")
    public String name;



    @Column(name = "images")
    public String images;

    @Column(name = "images1")
    public String images1;

    @Column(name = "images2")
    public String images2;

    @Column(name = "images3")
    public String images3;



    @Column(name = "description")
    public String description;

    @Column(name = "price")
    public double price;

    @Column(name = "sold")
    public boolean is_sold;

    @Column(name = "updated_at")
    public Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

    @ManyToOne
    @JoinColumn(name = "user_prize")
    public User userPrize;


}

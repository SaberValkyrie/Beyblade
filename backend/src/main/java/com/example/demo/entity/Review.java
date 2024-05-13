package com.example.demo.entity;
import javax.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "images_review")
    public String imagesReview;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    @Column(name = "order_id")
    public int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "rating")
    public byte rating;

    @Column(name = "comment")
    public String comment;

    @Column(name = "created_at")
    public Timestamp createdAt;

}

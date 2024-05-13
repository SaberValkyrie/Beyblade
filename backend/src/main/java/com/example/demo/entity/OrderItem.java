    package com.example.demo.entity;
    import javax.persistence.*;
    import lombok.Data;

    @Entity
    @Data
    @Table(name = "order_items")
    public class OrderItem {

        @ManyToOne
        @JoinColumn(name = "order_id")
        public Order order; // trường này để quan hệ ngược với Order

        @ManyToOne
        @JoinColumn(name = "product_id")
        public Product product;
        
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        public Long id;


    }

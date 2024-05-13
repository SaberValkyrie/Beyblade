package com.example.demo.entity;
import javax.persistence.*;

import com.example.demo.repository.order.OrderRepository;
import com.example.demo.service.OrderService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    public int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "order_details_id")
    public OrderDetails details;

    @Column(name = "order_date")
    public Timestamp date;

    @Column(name = "order_time_update")
    public Timestamp updateTime;

    @Column(name = "order_key")
    public String key;
}

package com.example.demo.repository.order;

import com.example.demo.entity.OrderDetails;
import com.example.demo.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemOrderRepository extends JpaRepository<OrderItem,Long> {

}
package com.example.demo.repository.order;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailsRepository extends JpaRepository<OrderDetails,Long> {

}
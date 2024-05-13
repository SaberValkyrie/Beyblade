package com.example.demo.repository.order;

import com.example.demo.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order,Long> {




}
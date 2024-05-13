package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.order.DetailsRepository;
import com.example.demo.repository.order.ItemOrderRepository;
import com.example.demo.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private DetailsRepository details;
    @Autowired
    private ItemOrderRepository item;
    public void save(Order order) {
        repository.save(order);
    }
    public void save(OrderDetails order) {
        details.save(order);
    }
    public void save(OrderItem order) {
        item.save(order);
    }

    public Order getOrderByKey(String key) {
        return null;
    }

    public List<OrderItem> getItemByOrder(Order order) {

        return null;
    }

    public OrderStatus getst(byte id) {

        return null;
    }

    public List<OrderStatus> getAllStatus() {

        return null;
    }

    public List<Order> getOrderByToken(User userToken,byte status) {
        return null;
    }

    public List<Order> getOrderBySeller(User userToken,byte status) {
        return null;
    }
    public List<Order> getOrderBySellerToDay(User userToken) {

        return null;

    }
    public List<Order> getAllOrder(User user) {
        return null;

    }
    public List<Order> getAllOrderBySeller(User user) {

        return null;

    }
    public List<Order> getOrderByUser(User userToken, byte status, Timestamp yesterdayStart, Timestamp todayStart) {
        return null;
    }
}

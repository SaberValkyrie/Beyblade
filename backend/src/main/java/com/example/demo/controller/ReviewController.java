package com.example.demo.controller;

import com.example.demo.dto.ResponseOpject;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.service.*;
import com.example.demo.support.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/review")
public class ReviewController {

    @Autowired
    private UserService userService;
    @Autowired
    private NotifyService notifyService;
//    @Autowired
//    private CartService cartService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("create/{token}/{id}/{key}")
    public ResponseEntity<ResponseOpject> getAllOrdersByTime(
            @PathVariable String token,
            @PathVariable int id,
            @PathVariable String key,
            @RequestBody Review review
            ) {
        User userToken = tokenService.getUserFromToken(token);
        if (userToken == null) {
            return Util.checkStatus(HttpStatus.UNAUTHORIZED, "Token sai", null);
        }
        Order order = orderService.getOrderByKey(key);
        if (order == null || order.orderId != id){
            return Util.checkStatusRes(HttpStatus.NOT_FOUND, "Không tìm thấy đơn hàng ", null);
        }
//        if (order.isRate){
//            return Util.checkStatusRes(HttpStatus.BAD_REQUEST, "Đơn Hàng Này Đã Đánh Giá Trước Đó Rồi", null);
//        }
        List<OrderItem> orderItem = orderService.getItemByOrder(order);
        Review rv = null;
        for (OrderItem item : orderItem){
          rv = new Review();
            rv.user = userToken;
            rv.imagesReview = review.imagesReview;
            rv.comment = review.comment;
            rv.rating = review.rating;
            rv.orderId = order.orderId;
            rv.product = item.product;
            rv.createdAt = new Timestamp(System.currentTimeMillis());
            reviewService.save(rv);
        }
//        order.isRate = true;
        orderService.save(order);
        return Util.checkStatusRes(HttpStatus.OK, "Tải lên đánh giá thành công", rv);
    }


}

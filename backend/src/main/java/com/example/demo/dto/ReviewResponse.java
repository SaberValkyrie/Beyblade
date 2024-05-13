package com.example.demo.dto;

import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
public class ReviewResponse {

    public long product;

    public String user;

    public long orderID;

    public int rating;

    public String comment;

    public Timestamp createdAt;

    public ReviewResponse(long product, String user, long orderID, int rating, String comment, Timestamp createdAt) {
        this.product = product;
        this.user = user;
        this.orderID = orderID;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}

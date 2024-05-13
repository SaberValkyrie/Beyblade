package com.example.demo.dto;

import com.example.demo.entity.*;

import java.util.List;

public class OrderRequest {
    public List<CartReponse> orderItems;
    public AddressInfo addressInfo;
    public long voucher; //voucher id

    public double ship;
}
